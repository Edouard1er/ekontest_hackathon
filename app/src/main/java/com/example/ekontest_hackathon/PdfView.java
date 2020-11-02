package com.example.ekontest_hackathon;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PdfView extends AppCompatActivity {

    PDFView pdfView;
    ProgressBar progressBar;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_pdfview);

        pdfView = (PDFView) findViewById(R.id.pdfviewtest);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        String fileName = getIntent().getStringExtra("fileName");
        System.out.println(fileName);

        StorageReference storageRef = storage.getReference();

        StorageReference islandRef = storageRef.child("Documents/" + fileName);

        islandRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                pdfView.fromBytes(bytes)
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .onLoad(new OnLoadCompleteListener() {
                            @Override
                            public void loadComplete(int nbPages) {
                                progressBar.setVisibility(View.GONE);
                            }
                        })
                        .scrollHandle(new DefaultScrollHandle(getApplicationContext()))
                        .spacing(4)
                        .swipeHorizontal(false)
                        .autoSpacing(false)
                        .load();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Toast.makeText(getApplicationContext(), "Cannot open pdf", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
