package com.example.ekontest_hackathon;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;


public class UserAdapter extends RecyclerView.Adapter< UserAdapter.UserHolder> {
    //private static ClickListener clickListener;
    public static final int  MSG_RECEIVER = 0;
    public static final int  MSG_SENDER = 1;

     String theLastMessage;
    private  Context context;
    private Boolean isChat;
    List <UserModel> mUser;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public UserAdapter(Context context, List <UserModel> mUser, Boolean isChat) {
        this.context=context;
        this.mUser=mUser;
        this.isChat=isChat;
    }

    @NonNull
    @Override
    public UserAdapter.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.UserHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
        UserModel u=mUser.get(position);
        holder.userName.setText(u.getUserName());
        holder.userImage.setImageResource(R.drawable.ic_launcher_background);
        holder.userId.setText(u.getId());
        getLastMessage(u.getId(), holder.lastMessage);
        //Toast.makeText(context, "The last message is: "+theLastMessage, Toast.LENGTH_SHORT).show();
        holder.lastMessage.setText(theLastMessage);


        if(isChat){
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



    }



    @Override
    public int getItemCount() {
        return mUser.size();
    }
    class UserHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        TextView userName;
        ImageView userImage;
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
            userImage=itemView.findViewById(R.id.cUserImage);
            userId = itemView.findViewById(R.id.UserId);

            lastMessage = itemView.findViewById(R.id.lastMessage);

            online=itemView.findViewById(R.id.statusOnline);
            offline=itemView.findViewById(R.id.statusOfline);

        }


        @Override
        public void onClick(View v) {
            TextView receiverId = (TextView) v.findViewById(R.id.UserId);

            Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
            intent.putExtra("receiver", receiverId.getText().toString());
            //Toast.makeText(DisplayUser.this, receiverId.getText().toString(), Toast.LENGTH_SHORT).show();

            v.getContext().startActivity(intent);


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private void getLastMessage(final String receiver, final TextView lastMessage){
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



}


