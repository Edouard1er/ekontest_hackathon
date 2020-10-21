package com.example.ekontest_hackathon;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UrlImageModel {
    public UrlImageModel() {
    }
    public String getUrlImage(String[] imageSource, final ImageView imageUser){
        final String[] url = new String[1];
        final StorageReference mStorageRef= FirebaseStorage.getInstance().getReference();


        if(imageSource[0].contains("firebasestorage.googleapis.com")){
            final StorageReference fileReference= mStorageRef.child("Images").child(imageSource[1]+"_200x200");
            fileReference.getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri2) {
                            url[0] =String.valueOf(uri2);
                            Glide.with(imageUser)
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

    }
}
