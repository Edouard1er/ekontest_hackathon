package com.example.ekontest_hackathon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> implements Filterable
{

    String freelanceName;
    private  Context context;
    private Boolean isChat;
    private ImageButton menuOp;
    List<StudentModel> userFiltered;
    List<StudentModel> mStudent;

    List <StudentModel> mUser;
   // CustomFilter filter;

    private static  boolean dialogOpened = false;


    public StudentAdapter(Context context, List <StudentModel> mUser) {
        this.context=context;
        this.mUser=mUser;
        this.userFiltered=mUser;
    }

    public StudentAdapter (){
        //Empty constructor
    }
    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        try {
            menuOp.setVisibility(View.GONE);
            StudentModel u = mUser.get(position);
            holder.userName.setText(u.getPersonalInformationModel().getFirstname() + " " + u.getPersonalInformationModel().getLastname());
            holder.userImage.setImageResource(R.drawable.username);
            holder.userId.setText(u.getId());
            holder.freelanceName_.setText("...");
            final String[] url = new String[2];
            url[0] = u.getPersonalInformationModel().getImagelink();
            url[1] = u.getPersonalInformationModel().getImagename();
            UrlImageModel urlImageModel = new UrlImageModel();
            urlImageModel.getUrlImage(url, holder.userImage,context, holder.altUserImage,holder.altTxtName,u);
            //getUrlImage(url, holder.userImage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public int getItemCount() {
        return mUser.size();
    }


    class StudentHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        TextView userName,altTxtName;
        ImageView userImage;
        ConstraintLayout altUserImage;
        TextView userId;

        TextView freelanceName_;
        public  StudentHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            context = itemView.getContext();

            userName=itemView.findViewById(R.id.cUserName);
            userImage=itemView.findViewById(R.id.cUserImage);
            userId = itemView.findViewById(R.id.UserId);
            freelanceName_ = itemView.findViewById(R.id.freelance_name);
            altUserImage=itemView.findViewById(R.id.altUserImage);
            altTxtName=itemView.findViewById(R.id.altTxtName);
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

                FirebaseDatabase.getInstance().getReference("Documents").child("NormalDoc").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                        //
                        for(DataSnapshot dataSnapshot1: snapshot1.getChildren()) {
                            NormalDocModel model = dataSnapshot1.getValue(NormalDocModel.class);
                            if(model.getIdUser().equals(user.getUid())) {
                                docName.add(model.getTitle());
                                docId.add(model.getIdDocument());
                                checked.add(false);
                            }
                        }
                        System.out.println("array id: " + docId);
                        System.out.println("array name: " + docName);
                        for(int j = 0; j < docId.size(); j++) {
                            final String idC =docId.get(j);
                            final int finalJ = j;
                            FirebaseDatabase.getInstance().getReference("Users").child(receiverId.getText().toString()).child("docAvailable").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                    System.out.println("checking snapshot: " + snapshot1);
                                    System.out.println("initial checked: " + checked);
                                    for(DataSnapshot snap: snapshot1.getChildren()) {
                                        String key = (String) snap.getValue();
                                        System.out.println("element: " + snap);
                                        System.out.println("element doc id: " + snap.getValue());
                                        if(key.equals(idC)) {
                                            checked.set(finalJ, true);
                                        }
                                    }
                                    System.out.println("final checked: " + checked);
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
                                            }

                                            if(String.valueOf(isChecked).equals("false")){
                                                //here we add the document to available doc list of the user
                                                FirebaseDatabase.getInstance().getReference("Users").child(receiverId.getText().toString()).child("docAvailable").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    builder.setCancelable(false);

                                    // Set a title for alert dialog
                                    builder.setTitle("Give doc access to student");

                                    // Set the neutral/cancel button click listener
                                    builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Do something when click the neutral button
                                            dialog.dismiss();
                                            dialogOpened = false;
                                        }
                                    });

                                    if(!dialogOpened) {
                                        builder.show();
                                        dialogOpened = true;
                                    }

//                                    AlertDialog dialog = builder.create();

//                                    if ((dialog == null) || !dialog.isShowing()){
////                                        m_dialog = new Dialog(...); // initiate it the way you need
//                                        dialog.show();
//                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            List<StudentModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(userFiltered);
                results.values=userFiltered;
                results.count=userFiltered.size();
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (StudentModel item : userFiltered) {

                    if (item.getPersonalInformationModel().getFirstname().toLowerCase().contains(filterPattern)||
                            item.getPersonalInformationModel().getLastname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
                results.values = filteredList;
                results.count=filteredList.size();

            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //mUser.clear();
            mUser=(ArrayList<StudentModel>) results.values;;
            notifyDataSetChanged();
        }
    };






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

  /*  public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new StudentAdapter.CustomFilter();
        }

        return filter;
    }
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                Log.e("Main"," data search: "+constraint.toString());

                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<StudentModel> filters=new ArrayList<StudentModel>();
                // filters.clear();
                //get specific items
                for(StudentModel user : userFiltered)
                {
                    if(user.getPersonalInformationModel().getFirstname().toUpperCase().contains(constraint) || user.getPersonalInformationModel().getLastname().toUpperCase().contains(constraint))
                    {
                        filters.add(user);
                    }
                }
                results.values=filters;
                results.count=filters.size();

            }else
            {    results.values=userFiltered;
                results.count=userFiltered.size();


            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub



            mStudent=(ArrayList<StudentModel>) results.values;
            notifyDataSetChanged();
        }

    }*/


}


