package com.example.ekontest_hackathon;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MessageAdapter extends RecyclerView.Adapter< MessageAdapter.ViewHolder> {
    //private static ClickListener clickListener;
    public static final int  MSG_RECEIVER = 0;
    public static final int  MSG_SENDER = 1;


    private  Context context;
    List <MessageModel> chats;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    public MessageAdapter(Context context, List <MessageModel> chats) {
        this.context=context;
        this.chats=chats;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == MSG_RECEIVER) {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_chat_item, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_item, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        MessageModel m=chats.get(position);
        holder.showMessage.setText(m.getMessage());
        holder.datetime.setText(getDate(m.getDatetime()));
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

        TextView showMessage;
        TextView datetime;

        public  ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            showMessage = itemView.findViewById(R.id.showMessage);
            datetime=itemView.findViewById(R.id.datetime);


        }

        @Override
        public void onClick(View v) {
            //Toast.makeText(context, "Wawwww", Toast.LENGTH_SHORT).show();


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


    @Override
    public int getItemViewType(int position) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(user.getUid())){
            return MSG_SENDER;
        }else{
            return MSG_RECEIVER;
        }

    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("hh:mm", cal).toString();

        return date;
    }


}


