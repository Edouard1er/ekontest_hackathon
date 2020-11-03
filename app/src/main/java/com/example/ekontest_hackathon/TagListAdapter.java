package com.example.ekontest_hackathon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


public class TagListAdapter extends RecyclerView.Adapter< TagListAdapter.TagHolder> {

    private  Context context;

    List <TagModel> mTag;

    public TagListAdapter(Context context, List <TagModel> mTag) {
        this.context=context;
        this.mTag=mTag;

    }

    public TagListAdapter (){
        //Empty constructor
    }
    @NonNull
    @Override
    public TagListAdapter.TagHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tag_item, parent, false);
        return new TagListAdapter.TagHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final TagListAdapter.TagHolder holder, int position) {
        try {
            TagModel tag = mTag.get(position);
            holder.tagText.setText(tag.getTag());
            //Toast.makeText(context, "Inside adapter", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.tagRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagModel model = new TagModel();
                model.removeTag(holder.tagText.getText().toString());
            }
        });

    }



    @Override
    public int getItemCount() {
        return mTag.size();
    }
    class TagHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        TextView tagText,tagRemove;


        TextView lastMessage;
        public  TagHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            context = itemView.getContext();

            tagText=itemView.findViewById(R.id.tagText);
            tagRemove=itemView.findViewById(R.id.tagRemove);

        }


        @Override
        public void onClick(View v) {
         /*   try {

                Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
                intent.putExtra("receiver", receiverId.getText().toString());
                //Toast.makeText(DisplayTag.this, receiverId.getText().toString(), Toast.LENGTH_SHORT).show();

                v.getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }*/


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


    public static class App {
        public static void finishApp(AppCompatActivity appCompatActivity) {
            appCompatActivity.finish();
        }
    }
}


