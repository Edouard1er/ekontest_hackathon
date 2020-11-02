package com.example.ekontest_hackathon;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class UrlImageModel {
    private StorageReference mStorageRef;
    private StorageTask mUploadTask;
    private   Uri uri;


    public UrlImageModel() {
    }
    public String getUrlImage(String[] imageSource, final ImageView imageUser, final Context context, final ConstraintLayout constraintLayout, final TextView textView){
        final String[] url = new String[1];
        final StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();

        try{
            if(imageSource[0].contains("firebasestorage.googleapis.com")){
                final StorageReference fileReference= mStorageRef.child("Images").child(imageSource[1]+"_500x500");
                fileReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri2) {
                                url[0] =String.valueOf(uri2);
                                Glide.with(context)
                                        .load(uri2)
                                        .centerCrop()
                                        .into(imageUser);
                                //     .apply(new RequestOptions().override(200,90))



                            }
                        });
                return url[0];
            }else{
                url[0] =imageSource[0];
                Glide.with(imageUser)
                        .load(url[0])
                        //  .apply(new RequestOptions().override(120,90))
                        .centerCrop()
                        .into(imageUser);
                if(url[0].length()==0){
                    constraintLayout.setVisibility(View.VISIBLE);
                    // holder.altTxtName.setText(u.getPersonalInformationModel().getLastname().charAt(0)+""+u.getPersonalInformationModel().getFirstname().charAt(0));
                    imageUser.setVisibility(View.GONE);

                }else{
                    Toast.makeText(context, "Not zero", Toast.LENGTH_SHORT).show();
                }

                return url[0];
            }
        }catch (Exception e){
            return url[0];
        }





    }
    public String getUrlImage(String[] imageSource, final ImageView imageUser, final Context context){
        final String[] url = new String[1];
        final StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();

        try{
            if(imageSource[0].contains("firebasestorage.googleapis.com")){
                final StorageReference fileReference= mStorageRef.child("Images").child(imageSource[1]+"_500x500");
                fileReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri2) {
                                url[0] =String.valueOf(uri2);
                                Glide.with(context)
                                        .load(uri2)
                                        .centerCrop()
                                        .into(imageUser);
                                //     .apply(new RequestOptions().override(200,90))



                            }
                        });
                return url[0];
            }else{
                url[0] =imageSource[0];
                Glide.with(imageUser)
                        .load(url[0])
                        //  .apply(new RequestOptions().override(120,90))
                        .centerCrop()
                        .into(imageUser);
              

                return url[0];
            }
        }catch (Exception e){
            return url[0];
        }





    }

    public void updateImageUser(String path) throws FileNotFoundException {
        uri = Uri.parse(path);
        mStorageRef= FirebaseStorage.getInstance().getReference();
        final String fileIdentity=String.valueOf(System.currentTimeMillis());
        Calendar c = Calendar.getInstance();

        final StorageReference fileReference= mStorageRef.child(fileIdentity);
        InputStream stream = new FileInputStream(new File(String.valueOf(uri)));
        mUploadTask=fileReference.putStream(stream)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUri=taskSnapshot.getStorage().getDownloadUrl();
                        fileReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri2) {
                                       uri2.toString();


                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                      //  Toast.makeText(ReviewActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        // double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        //mProgressBar.setProgress((int)progress);

                    }
                });



    }
    public String loadFavoriteImage(String[] imageSource, final ImageView imageUser, final Context context){
        final String[] url = new String[1];
        final StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();

        try{
            if(imageSource[0].contains("firebasestorage.googleapis.com")){
                final StorageReference fileReference= mStorageRef.child("Images").child(imageSource[1]+"_500x500");
                fileReference.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri2) {
                                url[0] =String.valueOf(uri2);
                                Glide.with(context)
                                        .load(uri2)
                                        .centerCrop()
                                        .into(imageUser);
                                //     .apply(new RequestOptions().override(200,90))



                            }
                        });
                return url[0];
            }else{
                url[0] =imageSource[0];
                Glide.with(imageUser)
                        .load(url[0])
                        //  .apply(new RequestOptions().override(120,90))
                        .centerCrop()
                        .into(imageUser);

                return url[0];
            }
        }catch (Exception e){
            return url[0];
        }



    }

}
