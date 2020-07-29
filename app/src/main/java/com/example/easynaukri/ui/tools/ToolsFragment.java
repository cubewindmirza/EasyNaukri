package com.example.easynaukri.ui.tools;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.easynaukri.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Stream;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    StorageReference storageReference1;
    PDFView pdfView;
    Button btnpdf;
    URL pdfurl;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        //checkpdf();
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_privacy, container, false);
        AppCompatActivity activity= (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Resume");
         pdfView=root.findViewById(R.id.pdf_view);
         btnpdf=root.findViewById(R.id.viewresume);
         btnpdf.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 checkpdf();
             }
         });
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    private void checkpdf() {
        storageReference1=storageReference.child(firebaseAuth.getUid()).child("pdf/resume");
        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    pdfurl=new URL(uri.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                new pdfviewer().execute(pdfurl.toString());
            }
        });
    }
  @SuppressLint("StaticFieldLeak")
  public class pdfviewer extends AsyncTask<String,Void,InputStream>{
      @Override
      protected void onPostExecute(InputStream inputStream) {
          super.onPostExecute(inputStream);
         pdfView.fromStream(inputStream).load();

      }

      @Override
      protected InputStream doInBackground(String... strings) {
          InputStream inputStream=null;
          try {
              URL url=new URL(strings[0]);
              HttpURLConnection connection= (HttpURLConnection) url.openConnection();
              if(connection.getResponseCode()==200){
                  inputStream=new BufferedInputStream(connection.getInputStream());
              }
          } catch (MalformedURLException e) {
              e.printStackTrace();
          } catch (IOException e) {
              return null;
          }
          return  inputStream;


      }
  }
}