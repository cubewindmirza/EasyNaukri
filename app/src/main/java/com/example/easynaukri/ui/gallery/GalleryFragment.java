package com.example.easynaukri.ui.gallery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;

import com.example.easynaukri.R;
import com.example.easynaukri.UserProfileData;
import com.example.easynaukri.color_management;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class GalleryFragment extends Fragment {

  int imagepickcode=1;
  TextView tv_profileusername,tv_profileemail,tv_profileedit,tv_profileEmailData,tv_profileFullnameData,tv_profileAddressData;
  Uri uri;
  int count=0;

  CircleImageView imageView_this;
    NavigationView navigationView;
    private GalleryViewModel galleryViewModel;
    SharedPreferences sharedPreferences;
    String MyPREFERENCES = "MyPrefs" ;
    Bitmap bitmap;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseStorage firebaseStorage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    LinearLayout linearLayout;
    color_management color_theme;
    public String PREF_KEY="Color";
    public String COLOR="color";
    SharedPreferences sp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_completeprofile, container, false);

       // Toast.makeText("this",editText.getText().toString(),Toast.LENGTH_LONG).show();
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        firebaseStorage=FirebaseStorage.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        storageReference=firebaseStorage.getReference();
        databaseReference=firebaseDatabase.getReference();
        tv_profileusername=view.findViewById(R.id.profile_username);
        tv_profileemail=view.findViewById(R.id.profile_email);
        //tv_profileedit=view.findViewById(R.id.profile_edit);
        imageView_this=view.findViewById(R.id.profile_circleimage);
        tv_profileFullnameData=view.findViewById(R.id.profile_fullnamedata);
        tv_profileEmailData=view.findViewById(R.id.profile_emaildata);
        tv_profileAddressData=view.findViewById(R.id.profile_addressdata);
        linearLayout=view.findViewById(R.id.complete_profile_theme);
        sp=getActivity().getSharedPreferences(PREF_KEY,Context.MODE_PRIVATE);
        color_theme=new color_management(getActivity());
        checkThemeColor();
        checkprofilephoto();
        checkuserandemail();
        //header_circle=headerview.findViewById(R.id.Header_Imageview);
       tv_profileusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //settextvieweditable();

            }
        });

        imageView_this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent i=new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,imagepickcode);


              // checkThemeShared();
            }
        });
    }

    private void checkuserandemail() {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String userid=firebaseUser.getUid();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                String username=data.Username;
                String email=data.Email;
                tv_profileusername.setText(username);
                tv_profileemail.setText(email);
                tv_profileEmailData.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child(userid).child("ProfileDetails").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data=dataSnapshot.getValue(UserProfileData.class);
                String fullname=data.FullNameData;
                //String email=data.Email;
                String address=data.CityData;
                tv_profileFullnameData.setText(fullname);
                tv_profileAddressData.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void checkprofilephoto(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        String userid=firebaseUser.getUid();
        storageReference.child(userid).child("images/profile pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(imageView_this);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( requestCode==imagepickcode && data!=null){
            uri=data.getData();
            Bitmap  bitmap;
            try {
                 bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uri);
                 imageView_this.setImageBitmap(bitmap);
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                String userid=firebaseUser.getUid();
                StorageReference storageReference1=storageReference.child(userid).child("images").child("profile pic");
                 UploadTask uploadTask=storageReference1.putFile(uri);
                 uploadTask.addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getContext(),"Unable to upload pic to firebase",Toast.LENGTH_LONG).show();
                     }
                 }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         Toast.makeText(getContext(),"Successfully uploaded to firebase",Toast.LENGTH_LONG).show();
                     }
                 });
            } catch (IOException e) {
                e.printStackTrace();
            }
           // imageView_this.setImageURI(data.getData());
           // getByteArrayFromImageURL(getContentRe);
           // convertUrlToBase64(data.getData().toString());
            /*SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("image","IMAGE");
            editor.apply();
            Toast.makeText(getContext(), "Image Picked", Toast.LENGTH_SHORT).show();

             */
        }
    }
   public void settextvieweditable(){
       tv_profileusername.setCursorVisible(true);
       tv_profileusername.setFocusableInTouchMode(true);
       tv_profileusername.setInputType(InputType.TYPE_CLASS_TEXT);
        tv_profileusername.requestFocus();

   }
  public void showprofiledialog(){
        String[] options ={"Edit UserName","Edit Email","Edit Password","Edit number"};
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose the options");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    showdialogeditext("Username");
                }
                else if(which==1){
                    showdialogeditext("Email");
                }
                else if(which==2){
                    showdialogeditext("Password");
                }
                else if(which==3){
                   showdialogeditext("Number");
                }
            }
        });
        builder.create().show();
  }
  public void showdialogeditext(final String key){
      AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
      builder.setTitle("Update "+key);
      LinearLayout linearLayout=new LinearLayout(getActivity());
      final EditText editText=new EditText(getActivity());
      editText.setHint("Enter "+key);
      linearLayout.setOrientation(LinearLayout.VERTICAL);
      linearLayout.addView(editText);
      builder.setView(linearLayout);
      builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
             String profilekey=editText.getText().toString();
             if(!TextUtils.isEmpty(profilekey)) {
                 HashMap<String,Object> result=new HashMap<>();
                  result.put(key, profilekey);
                 FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                 String userid=firebaseUser.getUid();
                 databaseReference.child(userid).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {
                          Toast.makeText(getContext(),"successfuly "+key+"updated",Toast.LENGTH_LONG).show();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         Toast.makeText(getContext(),"some error while updating",Toast.LENGTH_LONG).show();
                     }
                 });
             }else
                 Toast.makeText(getContext(),"Please fill ",Toast.LENGTH_LONG).show();
          }
      }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
              Toast.makeText(getContext(),"Nothing updated",Toast.LENGTH_LONG).show();
          }
      });
      builder.create().show();
  }
  public void checkThemeColor(){
        String blue="blue";
        String red="red";
        String green="green";
        String orange="orange";
        String yellow="yellow";
        String dark="dark";
        String brown="brown";
        String color=color_theme.getColor();
        if(color.equals(blue)){
            linearLayout.setBackgroundResource(R.drawable.gradient_navheader);
        }
      else if(color.equals(red)){
          linearLayout.setBackgroundResource(R.drawable.red_gradient_theme);
      }
      else if(color.equals(green)){
          linearLayout.setBackgroundResource(R.drawable.green_gradient_themen);
      }
      else if(color.equals(orange)){
          linearLayout.setBackgroundResource(R.drawable.orange_gradient_themen);
      }
        else if(color.equals(yellow)){
            linearLayout.setBackgroundResource(R.drawable.yellow_gradient_themen);
        }
        else if(color.equals(dark)){
            linearLayout.setBackgroundResource(R.drawable.dark_gradient_themen);
        }
        else if(color.equals(brown)){
            linearLayout.setBackgroundResource(R.drawable.brown_gradient_themen);
        }
  }
  public void checkThemeShared(){
        String color=sp.getString(COLOR,"blue");
        if(color.equals("blue")){
            linearLayout.setBackgroundResource(R.drawable.gradient_navheader);
        }
        else if(color.equals("red")){
            linearLayout.setBackgroundResource(R.drawable.red_gradient_theme);
        }
        else if(color.equals("green")){
            linearLayout.setBackgroundResource(R.drawable.green_gradient_themen);
        }
        else if(color.equals("orange")){
            linearLayout.setBackgroundResource(R.drawable.orange_gradient_themen);
        }
  }
}