package com.example.easynaukri;

import android.Manifest;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.easynaukri.ui.InternetConnection;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class logged_in_page extends AppCompatActivity   {
    database_helper db = new database_helper(this);
    int permissioncode = 100;
    int imagepickcode = 200;
    int pdfpickcode = 300;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView headertv;
    View headerview;
    CircleImageView circleImageView;
    Uri imagepath;

    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    int GOOGLE_PAY_REQUEST_CODE = 123;
    String upiid="8317359648@paytm";
    String amount="1";
    String transid="100";

    String MyPREFERENCES = "MyPrefs";
    Bitmap bitmap;
    private AppBarConfiguration mAppBarConfiguration;
    String username;
    SQLiteDatabase database;
    String foundname = "";
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    SessionMangament sessionMangament;
    color_management color_theme;
    LinearLayout linearLayout;
    InternetConnection connection;
    ProgressDialog internetdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.hide();
        connection=new InternetConnection();
        internetdialog=new ProgressDialog(this);
        internetdialog.setTitle("Internet Issue");
        internetdialog.setMessage("Turn On Internet");
        checkInternet();
        linearLayout = findViewById(R.id.complete_profile_theme);
        sessionMangament = new SessionMangament(logged_in_page.this);
        color_theme = new color_management(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        checkprofilephoto();
        checkprofileusername();

        String Email = getIntent().getStringExtra("NAME");
        String Password = getIntent().getStringExtra("PASSWORD");
        database = db.getReadableDatabase();
        // foundname=db.username(Email,Password);
        //cursor.moveToNext();
        // foundname=cursor.getString(0);

        //foundname=db.username(Email);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                */
                selectcolor();
            }
        });
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerview = navigationView.getHeaderView(0);
        headertv = headerview.findViewById(R.id.user_email);
        headertv.setText(foundname);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open_drawer,R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        // sharedPreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        // headertv.setText(sharedPreferences.getString("image","NOT FOUND"));
        circleImageView = headerview.findViewById(R.id.Header_Imageview);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

       mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools,  R.id.nav_logout,R.id.nav_job,R.id.nav_aboutus,R.id.nav_refer)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
       // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        checkThemeColor();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 2);
            }
        });
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int id = destination.getId();
                switch (id) {
                    case R.id.nav_home: {
                        // Toast.makeText(logged_in_page.this, getIntent().getStringExtra("NAME"), Toast.LENGTH_SHORT).show();

                        break;
                    }
                    case R.id.nav_logout: {
                        sessionMangament.setsession(false);
                        firebaseAuth.signOut();
                        Intent i = new Intent(logged_in_page.this, MainActivity.class);
                        startActivity(i);
                        finishAffinity();
                        finish();

                        break;
                    }
                    case R.id.nav_gallery: {
                     /* Intent i=new Intent(Intent.ACTION_PICK);
                       i.setType("image/*");
                       startActivityForResult(i,imagepickcode);
                       break;

                      */
                        //getRegisteredData();

                        break;


                    }
                    case R.id.nav_slideshow: {
                       // payUsingUpi();
                        break;
                    }
                    case R.id.nav_tools: {
                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.setType("application/pdf");
                        i.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(i.createChooser(i, "select resume pdf"), pdfpickcode);
                        break;
                    }

                }
            }
        });
        if (ContextCompat.checkSelfPermission(logged_in_page.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "Permission Already Granted", Toast.LENGTH_SHORT).show();
        } else {
            requeststoragepersimmisons();
        }
    }

    private void getRegisteredData() {
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        DatabaseReference myRef = database.getReference().child(useruid);
        // FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        // String userkey=firebaseUser.getUid();
        myRef.addValueEventListener(new ValueEventListener() {
            private static final String TAG = "data";

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //userdata data=new userdata();
                // String value = dataSnapshot.getValue();
                //Log.d(TAG, "Value is: " + value);
                UserProfileData userProfileData = dataSnapshot.getValue(UserProfileData.class);
                String username = userProfileData.Username;
                String email = userProfileData.Email;
                String password = userProfileData.Password;
                String number = userProfileData.Number;
                String data = username + "\n" + email + "\n" + password + "\n" + number;
                Toast.makeText(logged_in_page.this, data, Toast.LENGTH_LONG).show();
                foundname = username;
                headertv.setText(username);
                //Toast.makeText(logged_in_page.this,data,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GOOGLE_PAY_REQUEST_CODE) {
            if ((RESULT_OK == resultCode) || (resultCode == GOOGLE_PAY_REQUEST_CODE)) {
                if (data != null) {
                    String trxt = data.getStringExtra("response");
                    Log.e("UPI", "onActivityResult: " + trxt);
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add(trxt);
                    upiPaymentDataOperation(dataList);
                } else {
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
            } else {
                //when user simply back without payment
                Log.e("UPI", "onActivityResult: " + "Return data is null");
                ArrayList<String> dataList = new ArrayList<>();
                dataList.add("nothing");
                upiPaymentDataOperation(dataList);
            }
        }
        if (resultCode == RESULT_OK && requestCode == imagepickcode && data != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                circleImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == pdfpickcode && data != null) {
            uploadresume(data.getData());
        } else if (resultCode == RESULT_OK && requestCode == 1) {
            Toast.makeText(this, "Image picked successfully code 1", Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_OK && requestCode == 2 && data != null) {
            try {
                /*assert data != null;
                String Url=data.getData().toString();
                URL url=new URL(Url);
                bitmap=BitmapFactory.decodeStream(url.openConnection().getInputStream());
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
                String base64= Base64.encodeToString(bos.toByteArray(),Base64.DEFAULT);
                Toast.makeText(this,base64,Toast.LENGTH_LONG).show();

                 */
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("uploading image");
                progressDialog.show();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                circleImageView.setImageBitmap(bitmap);
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String userid = firebaseUser.getUid();
                StorageReference storageReference1 = storageReference.child(userid).child("images").child("profile pic");
                imagepath = data.getData();
                UploadTask uploadTask = storageReference1.putFile(imagepath);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.cancel();
                        Toast.makeText(logged_in_page.this, "Failed to upload", Toast.LENGTH_LONG).show();
                    }
                });
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.cancel();
                        Toast.makeText(logged_in_page.this, "Success to upload", Toast.LENGTH_LONG).show();
                    }
                });
                uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double per = ((100 * taskSnapshot.getBytesTransferred()) / (double) taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage((int) per + "% Uploaded..");
                        progressDialog.show();
                    }
                });

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void requeststoragepersimmisons() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setMessage("We Need This Permission To Upload Ur Picture");
            builder.setTitle("Permission Needed");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(logged_in_page.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
            });
            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
           getMenuInflater().inflate(R.menu.theme_rate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_theme) {
            selectcolor();
        }
        return true;
    }
    private void checkInternet() {
        if(connection.connected(getApplicationContext())){
            internetdialog.cancel();
            //Toast.makeText(this,"Internet is Active",Toast.LENGTH_SHORT).show();
        }
        else
        {
            internetdialog.show();

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
           // builder.setTitle("LOGOUT");
            builder.setCancelable(false);
            //builder.setMessage("Do You Want To Logout");
            final View customLayout = getLayoutInflater().inflate(R.layout.custom_alertdialog, null);
            builder.setView(customLayout);
            Button logoutyes=customLayout.findViewById(R.id.logout_yes);
            Button logoutno=customLayout.findViewById(R.id.logout_no);
            final AlertDialog dialog;
            dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            logoutyes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(logged_in_page.this, MainActivity.class);
                    firebaseAuth.signOut();
                    sessionMangament.setsession(false);
                    finishAffinity();
                    finish();
                    startActivity(i);
                }
            });
            logoutno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            /*builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(logged_in_page.this, MainActivity.class);
                    firebaseAuth.signOut();
                    sessionMangament.setsession(false);
                    finishAffinity();
                    finish();
                    startActivity(i);

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

             */

        }

    }

    public void checkprofilephoto() {
        storageReference.child(firebaseAuth.getUid()).child("images/profile pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(circleImageView);
            }
        });
    }

    public void checkprofileusername() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userid = firebaseUser.getUid();
        databaseReference.child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfileData data = dataSnapshot.getValue(UserProfileData.class);
                String username = data.Username;
                headertv.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void uploadresume(Uri uri) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading resume");
        progressDialog.show();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String useruid = firebaseUser.getUid();
        StorageReference storageReference1 = storageReference.child(useruid).child("pdf/resume");
        UploadTask uploadTask = storageReference1.putFile(uri);
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double per = ((100 * taskSnapshot.getBytesTransferred()) / (double) taskSnapshot.getTotalByteCount());
                progressDialog.setMessage((int) per + "% Uploaded...");
                progressDialog.show();
            }
        });
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.cancel();
                Toast.makeText(logged_in_page.this, "Resume uploaded successfully", Toast.LENGTH_SHORT).show();
            }
        });
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.cancel();
                Toast.makeText(logged_in_page.this, "Some error while uploading try again", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void selectcolor() {
        String[] options = {"Red", "Blue", "Green", "orange","yellow","dark","brown"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Colors");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    changecolor("Red");
                } else if (which == 1) {
                    changecolor("Blue");
                } else if (which == 2) {
                    changecolor("Green");
                } else if (which == 3) {
                    changecolor("Orange");
                }
                else if (which == 4) {
                    changecolor("Yellow");
                }
                else if (which == 5) {
                    changecolor("Dark");
                }
                else if (which == 6) {
                    changecolor("Brown");
                }
            }
        });
        builder.create().show();
    }

    public void changecolor(String color) {
        if (color.equals("Red")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.action_bar);
            headerview.setBackgroundResource(R.drawable.red_gradient_theme);
            color_theme.setColor("red");
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        } else if (color.equals("Blue")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.gradient_actionbar);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.gradient_navheader);
            color_theme.setColor("blue");
        } else if (color.equals("Green")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.green_gradient_themea);
           // setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.green_gradient_themen);
            color_theme.setColor("green");
        } else if (color.equals("Orange")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.orange_gradient_themea);
           // setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.orange_gradient_themen);
            color_theme.setColor("orange");
        }
        else if (color.equals("Yellow")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.yellow_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.yellow_gradient_themen);
            color_theme.setColor("yellow");
        }
        else if (color.equals("Dark")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.dark_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.dark_gradient_themen);
            color_theme.setColor("dark");
        }
        else if (color.equals("Brown")) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.brown_gradient_themea);
           // setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.brown_gradient_themen);
            color_theme.setColor("brown");
        }
    }

    public void checkThemeColor() {
        String blue = "blue";
        String red = "red";
        String green = "green";
        String orange = "orange";
        String yellow="yellow";
        String dark="dark";
        String brown="brown";
        String color = color_theme.getColor();
        if (color.equals(blue)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.gradient_actionbar);
           // setSupportActionBar(toolbar);

            headerview.setBackgroundResource(R.drawable.gradient_navheader);
        } else if (color.equals(red)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.action_bar);
           // setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.red_gradient_theme);
            //color_theme.setColor("red");
        }
        else if (color.equals(green)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.green_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.green_gradient_themen);
            //color_theme.setColor("green");
        }
        else if (color.equals(orange)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.orange_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.orange_gradient_themen);
            //.setColor("orange");
        }
        else if (color.equals(yellow)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.yellow_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.yellow_gradient_themen);
            //.setColor("orange");
        }
        else if (color.equals(dark)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.dark_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.dark_gradient_themen);
        }
        else if (color.equals(brown)) {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.drawable.brown_gradient_themea);
            //setSupportActionBar(toolbar);
            headerview.setBackgroundResource(R.drawable.brown_gradient_themen);
        }
    }
    public void payUsingUpi(){
        Calendar c = Calendar.getInstance();
       // SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
       transid=c.getTime().toString();
        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa", upiid)
                        .appendQueryParameter("pn", "mirza")
                        .appendQueryParameter("mc", "")
                        .appendQueryParameter("tr", transid)
                        .appendQueryParameter("tn", "your-transaction-note")
                        .appendQueryParameter("am", amount)
                        .appendQueryParameter("cu", "INR")
                        .appendQueryParameter("url", "your-transaction-url")
                        .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        //intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

// will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

// check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);
        } else {
            Toast.makeText(logged_in_page.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    public void upiPaymentDataOperation(ArrayList<String> data) {
        if (connection.connected(logged_in_page.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String useruid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
                HashMap<String,Object> result=new HashMap<String,Object>();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                String payment="Done";
                String Date=strDate;
                result.put("Payment",payment);
                result.put("Date",strDate);
                result.put("TransactionId",transid);
                myRef.updateChildren(result);
                Toast.makeText(logged_in_page.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
               // Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String useruid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
                HashMap<String,Object> result=new HashMap<String,Object>();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                String payment="Not Done";
                String cancel="Canceled by user";
                result.put("Payment",payment);
                result.put("Cancel",cancel);
                result.put("Date",strDate);
                result.put("TransactionId",transid);
                myRef.updateChildren(result);
                Toast.makeText(logged_in_page.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
               // Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                String useruid = firebaseUser.getUid();
                DatabaseReference myRef = database.getReference().child(useruid).child("PaymentDetails");
                HashMap<String,Object> result=new HashMap<String,Object>();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String strDate = sdf.format(c.getTime());
                String payment="Not Done";
                String cancel="Some error while transation";
                result.put("Payment",payment);
                result.put("Cancel",cancel);
                result.put("Date",strDate);
                result.put("TransactionId",transid);
                myRef.updateChildren(result);
                Toast.makeText(logged_in_page.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
               // Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            //Log.e("UPI", "Internet issue: ");

            Toast.makeText(logged_in_page.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }
    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_alertdialog, null);
        alertDialog.setView(customLayout);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // send data from the AlertDialog to the Activity
                Toast.makeText(getApplicationContext(),"Awesome ",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }


}