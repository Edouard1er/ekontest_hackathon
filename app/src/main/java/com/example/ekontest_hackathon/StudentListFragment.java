package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentListFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("Students");
    private StudentAdapter adapter_;
    List<StudentModel> mUsers=new ArrayList<>();
    RecyclerView recyclerView;
    SearchView searchStudents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);
        searchStudents = (SearchView) view.findViewById(R.id.search_student);

       /* SearchView searchView = (SearchView) searchStudents.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);*/
        searchStudents.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_.getFilter().filter(newText);
                return false;
            }
        });


       /* searchStudents.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter_.getFilter().filter(s);
                return false;
            }
        });*/
        setDisplayUsers(view);
        return view;
    }

    private void setDisplayUsers(View v){

        recyclerView = v.findViewById(R.id.student_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        System.out.println("Inside set display");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                System.out.println("Snapshot: " + snapshot);
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String stId = dataSnapshot.getKey();
                    FirebaseDatabase.getInstance().getReference("Users").child(stId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            System.out.println("Student user: " + snapshot);
                            StudentModel u = snapshot.getValue(StudentModel.class);
                            mUsers.add(u);
                            adapter_.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                adapter_= new StudentAdapter(getContext(), mUsers);
                recyclerView.setAdapter(adapter_);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
/*
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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.UserHolder> {
    //private static ClickListener clickListener;
    public static final int  MSG_RECEIVER = 0;
    public static final int  MSG_SENDER = 1;

    String freelanceName;
    private  Context context;
    private Boolean isChat;
    private ImageButton menuOp;
    List<StudentModel> userFiltered;
    List<StudentModel> mStudent;

    List <StudentModel> mUser;
   // CustomFilter filter;

    private static  boolean dialogOpened = false;


    public StudentAdapter(Context context, List <StudentModel> mUser, Boolean isChat) {
        this.context=context;
        this.mUser=mUser;
        this.userFiltered=mUser;
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
    class UserHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        TextView userName,altTxtName;
        ImageView userImage;
        ConstraintLayout altUserImage;
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



 */
