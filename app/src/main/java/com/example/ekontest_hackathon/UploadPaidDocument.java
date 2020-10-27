package com.example.ekontest_hackathon;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UploadPaidDocument extends AppCompatActivity {

    int REQUEST_CODE = 2342;
    ImageButton getDocumentButton;
    Button submitButton;
    Uri uri;
    String fileName;
    EditText editTextDocumentName, editTextDocumentTitle, editTextDocumentPrice;
    ProgressBar progressBar;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_paid_document);

        getDocumentButton = (ImageButton) findViewById(R.id.getDocumentButton);
        editTextDocumentName = (EditText) findViewById(R.id.editTextDocumentName);
        editTextDocumentTitle = (EditText) findViewById(R.id.editTextDocumentTitle);
        editTextDocumentPrice = (EditText) findViewById(R.id.editTextDocumentPrice);
        submitButton = (Button) findViewById(R.id.uploadButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        mStorageReference = FirebaseStorage.getInstance().getReference();
    }

    public void showFileChooser(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        String mimeTypes = "application/pdf";
        intent.setType(mimeTypes);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // If the user doesn't pick a file just return
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE || resultCode != RESULT_OK) {
            return;
        }

        // Import the file
        uri = data.getData();
        fileName = getFileName(data.getData());
        editTextDocumentName.setText(fileName);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void submitPdf(View view) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        final String dateAdded = dtf.format(now);
        final String scanId = String.valueOf(System.currentTimeMillis());
        final String filename =  user.getUid() + scanId + ".pdf";
        final String documentTitle = editTextDocumentTitle.getText().toString();
        final String documentName = editTextDocumentName.getText().toString();
        final float price = Float.valueOf(editTextDocumentPrice.getText().toString());
        final ArrayList<String> url = new ArrayList<String>();
        final String status = "Submitted";
        final String sharingCode = "";

        progressBar.setVisibility(View.VISIBLE);
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + filename);
        sRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        sRef.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        System.out.println("url du doc: " + uri.toString());
                                        url.add(uri.toString());
                                        PaidDocModel model = new PaidDocModel(filename, dateAdded, documentTitle, documentName,
                                                                            status, sharingCode, url.get(0), price, scanId,
                                                                                "",user.getUid(),"");
                                        //To-do: test if everything is ok in the insert
                                        model.insertPaidDoc(model);
                                        Toast.makeText(getApplicationContext(), "File submitted Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        progressBar.setProgress((int)progress);
                    }
                });
    }

    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

        cursor.close();

        return fileName;
    }
}