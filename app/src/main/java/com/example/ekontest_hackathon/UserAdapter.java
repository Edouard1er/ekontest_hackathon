package com.example.ekontest_hackathon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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


public class UserAdapter extends RecyclerView.Adapter< UserAdapter.UserHolder> {
    //private static ClickListener clickListener;
    public static final int  MSG_RECEIVER = 0;
    public static final int  MSG_SENDER = 1;

     String theLastMessage;
    private  Context context;
    private Boolean isChat;
    List <UserModel> mUser;

    public UserAdapter(Context context, List <UserModel> mUser, Boolean isChat) {
        this.context=context;
        this.mUser=mUser;
        this.isChat=isChat;
    }

    public UserAdapter (){
        //Empty constructor
    }
    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.UserHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
        try {
            UserModel u = mUser.get(position);
            holder.userName.setText(u.getPersonalInformationModel().getFirstname() + " " + u.getPersonalInformationModel().getLastname());
            holder.userImage.setImageResource(R.drawable.username);
            holder.userId.setText(u.getId());
            getLastMessage(u.getId(), holder.lastMessage);
            final String[] url = new String[2];
            url[0] = u.getPersonalInformationModel().getImagelink();
            url[1] = u.getPersonalInformationModel().getImagename();
            if(url[0].length()!=0 || url[1].length()!=0){
                UserAdapter userAdapter= new UserAdapter();
                //   userAdapter.getUrlImage(model.getPersonalInformationModel().getImagelink(),imageFreelancer);
                //userAdapter.getUrlImage(url,imageFreelancer);
                holder.altUserImage.setVisibility(View.GONE);
                getUrlImage(url, holder.userImage);

            }else{
                holder.userImage.setVisibility(View.GONE);
                holder.altUserImage.setVisibility(View.VISIBLE);
               holder.altTxtName.setText(u.getPersonalInformationModel().getLastname().charAt(0)+""+u.getPersonalInformationModel().getFirstname().charAt(0));
            }
            //getUrlImage(u.getPersonalInformationModel().getImagename(), holder.userImage);

            //Toast.makeText(context, "The last message is: "+theLastMessage, Toast.LENGTH_SHORT).show();
            holder.lastMessage.setText(theLastMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*if(isChat){
            if(u.getStatus().equals("online")){
                holder.online.setVisibility(View.GONE);
                holder.offline.setVisibility(View.GONE);

            }else{
                holder.offline.setVisibility(View.GONE);
                holder.online.setVisibility(View.GONE);
            }

        }else{
            holder.offline.setVisibility(View.GONE);
            holder.online.setVisibility(View.GONE);
        }
*/
    }



    @Override
    public int getItemCount() {
        return mUser.size();
    }
    class UserHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        TextView userName,altTxtName;
        ImageView userImage;
        ConstraintLayout altUserImage;

        TextView userId;

        TextView online;
        TextView offline;

        TextView lastMessage;
        public  UserHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            context = itemView.getContext();

            userName=itemView.findViewById(R.id.cUserName);
            altTxtName=itemView.findViewById(R.id.altTxtName);
            userImage=itemView.findViewById(R.id.cUserImage);
            altUserImage=itemView.findViewById(R.id.altUserImage);
            altTxtName=itemView.findViewById(R.id.altTxtName);


            userId = itemView.findViewById(R.id.UserId);

            lastMessage = itemView.findViewById(R.id.lastMessage);

            online=itemView.findViewById(R.id.statusOnline);
            offline=itemView.findViewById(R.id.statusOfline);

        }


        @Override
        public void onClick(View v) {
            try {
                TextView receiverId = (TextView) v.findViewById(R.id.UserId);

                Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
                intent.putExtra("receiver", receiverId.getText().toString());
                //Toast.makeText(DisplayUser.this, receiverId.getText().toString(), Toast.LENGTH_SHORT).show();

                v.getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private void getLastMessage(final String receiver, final TextView lastMessage){
       final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        theLastMessage = "default";
        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats");

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    if((model.getReceiver().equals(receiver) && model.getSender().equals(user.getUid())) || (model.getReceiver().equals(user.getUid()) && model.getSender().equals(receiver))){
                        theLastMessage =model.getMessage();
                    }
                }

                switch (theLastMessage){
                    case "default":
                        lastMessage.setText("No Message");
                        break;
                    default:
                        lastMessage.setText(theLastMessage);
                        break;
                }
                theLastMessage = "default";


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                                                           //     .apply(new RequestOptions().override(200,90))



                        }
                    });
        /*if(user.getPhotoUrl().toString().equals(imagename)){
            url[0] = imagename;
            Glide.with(imageUser)
                    .load(imagename)
                    .into(imageUser);
        }else{

        }*/
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

    public static class App {
        public static void finishApp(AppCompatActivity appCompatActivity) {
            appCompatActivity.finish();
        }
    }
}


