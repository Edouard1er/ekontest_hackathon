package com.example.ekontest_hackathon;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PdfView extends AppCompatActivity {

    PDFView pdfView;
    FirebaseStorage storage = FirebaseStorage.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);

        setContentView(R.layout.activity_pdfview);

        pdfView = (PDFView) findViewById(R.id.pdfviewtest);

        String fileName = getIntent().getStringExtra("fileName");
        System.out.println(fileName);

        StorageReference storageRef = storage.getReference();

        StorageReference islandRef = storageRef.child("Documents/" + fileName);

        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                pdfView.fromBytes(bytes)
                        .defaultPage(0)
                        .enableAnnotationRendering(true)
                        .scrollHandle(new DefaultScrollHandle(getApplicationContext()))
                        .spacing(2)
                        .swipeHorizontal(true)
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
