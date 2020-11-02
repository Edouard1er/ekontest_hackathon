package com.example.ekontest_hackathon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.UserHolder> {
    //private static ClickListener clickListener;
    public static final int  MSG_RECEIVER = 0;
    public static final int  MSG_SENDER = 1;

    String freelanceName;
    private  Context context;
    private Boolean isChat;
    private ImageButton menuOp;
    List <UserModel> mUser;

    public StudentAdapter(Context context, List <UserModel> mUser, Boolean isChat) {
        this.context=context;
        this.mUser=mUser;
        this.isChat=isChat;
    }

    public StudentAdapter (){
        //Empty constructor
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
         return new StudentAdapter.UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.UserHolder holder, int position) {
        try {
            menuOp.setVisibility(View.GONE);
            UserModel u = mUser.get(position);
            holder.userName.setText(u.getPersonalInformationModel().getFirstname() + " " + u.getPersonalInformationModel().getLastname());
            holder.userImage.setImageResource(R.drawable.username);
            holder.userId.setText(u.getId());
            holder.freelanceName_.setText("...");
            final String[] url = new String[2];
            url[0] = u.getPersonalInformationModel().getImagelink();
            url[1] = u.getPersonalInformationModel().getImagename();
            getUrlImage(url, holder.userImage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return mUser.size();
    }
    class UserHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        TextView userName;
        ImageView userImage;
        TextView userId;

        TextView freelanceName_;
        public  UserHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            context = itemView.getContext();

            userName=itemView.findViewById(R.id.cUserName);
            userImage=itemView.findViewById(R.id.cUserImage);
            userId = itemView.findViewById(R.id.UserId);
            freelanceName_ = itemView.findViewById(R.id.freelance_name);
            menuOp = itemView.findViewById(R.id.imageButton1);
        }


        @Override
        public void onClick(final View v) {
            try {
                final TextView receiverId = (TextView) v.findViewById(R.id.UserId);
                Toast.makeText(v.getContext(), "User id: " + receiverId.getText().toString(), Toast.LENGTH_SHORT).show();
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final List<String> docName = new ArrayList<String>();
                final List<String> docId = new ArrayList<String>();
                final List<Boolean> checked = new ArrayList<Boolean>();
                FirebaseDatabase.getInstance().getReference("Documents").child("NormalDoc").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            NormalDocModel model = dataSnapshot.getValue(NormalDocModel.class);
                            if(model.getIdUser().equals(user.getUid())) {
                                docName.add(model.getTitle());
                                docId.add(model.getIdDocument());
                                checked.add(false);
                            }
                        }
                        System.out.println("array id: " + docId);
                        System.out.println("array name: " + docName);
                        //convert list to array
                         final boolean[] checked_ = new boolean[checked.size()];
                         final String[] docName_ = new String[checked.size()];
                         for(int i = 0; i < checked.size(); i++) {
                             checked_[i] = checked.get(i);
                             docName_[i] = docName.get(i);
                         }
                        builder.setMultiChoiceItems(docName_, checked_, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                System.out.println("New state:" + isChecked);
                                // Get the current focused item
                                final String currentItem = docId.get(which);
                                if(String.valueOf(isChecked).equals("true")) {
                                    //here we add the document to available doc list of the user
                                    FirebaseDatabase.getInstance().getReference("Users").child(receiverId.getText().toString()).child("docAvailable").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            FirebaseDatabase.getInstance().getReference("Users").child(receiverId.getText().toString()).child("docAvailable").push().setValue(currentItem);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {
                                    //here we add the document to available doc list of the user
                                    FirebaseDatabase.getInstance().getReference("Users").child(receiverId.getText().toString()).child("docAvailable").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                                String idDoc_ = (String) dataSnapshot.getValue();
                                                if(idDoc_.equals(currentItem)) {
                                                    FirebaseDatabase.getInstance().getReference("Users").child(receiverId.getText().toString()).child("docAvailable").child(dataSnapshot.getKey()).removeValue();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                // Update the current focused item's checked status
                                checked_[which] =  isChecked;

                                // Notify the current action
                                Toast.makeText(v.getContext(),
                                        currentItem + " " + isChecked, Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Specify the dialog is not cancelable
                        builder.setCancelable(true);

                        // Set a title for alert dialog
                        builder.setTitle("Give doc access to student");

                        // Set the positive/yes button click listener
                        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when click positive button
                                for (int i = 0; i<checked_.length; i++){
                                    boolean checked = checked_[i];
                                    if (checked) {
                                        System.out.println("document: " + docName.get(i));
                                    }
                                }
                            }
                        });

                        // Set the neutral/cancel button click listener
                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do something when click the neutral button
                                dialog.cancel();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        // Display the alert dialog on interface
                        dialog.show();

                        builder.show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }




    public String getUrlImage(String[] imageSource, final ImageView imageUser){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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

                        }
                    });
            return url[0];
        }else{
            url[0] =imageSource[0];
            Glide.with(imageUser)
                    .load(url[0])
                    .centerCrop()
                    .into(imageUser);

            return url[0];
        }

    }

    public static class App {
        public static void finishApp(AppCompatActivity appCompatActivity) {
            appCompatActivity.finish();
        }
    }
}


