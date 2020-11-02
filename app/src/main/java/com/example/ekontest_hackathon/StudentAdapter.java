package com.example.ekontest_hackathon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
                TextView receiverId = (TextView) v.findViewById(R.id.UserId);
                Toast.makeText(v.getContext(), "User id: " + receiverId.getText().toString(), Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Select document for this student:");

                // Set up the input
                final EditText input = new EditText(v.getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(v.getContext(), "Setting doc for...", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
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


