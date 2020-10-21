package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");

    private UserAdapter adapter;
    List<FreelancerModel> mUsers=new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    UserModel userModel;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayList<String> autoCompleteListe ;
    Button searchButton;


    GridView mGridView;
    FreelancerListAdapter adapterGrid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_page, container, false);
        autoCompleteTextView =(AutoCompleteTextView) view.findViewById(R.id.search_freelancer);
        searchButton =(Button) view.findViewById(R.id.searchResult);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "You click", Toast.LENGTH_SHORT).show();
                getUserMatchingtheSearchTerm(autoCompleteTextView.getText().toString());
                hideKeybaord(v);
            }
        });
        autoCompleteListe =new ArrayList<>();
       /* ArrayList<String> a =new ArrayList<>();
        a.add("Amos");
        a.add("Jodler");*/
        TagModel tagModel = new TagModel();
        /*tagModel.InsertTag("Histoire Moderne");
        tagModel.InsertTag("Chimie Organique");
        tagModel.InsertTag("Mathematique Financiere");*/


        getListTags();
        autoCompleteAdapter  = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_dropdown_item_1line,autoCompleteListe);
        autoCompleteTextView.setAdapter(autoCompleteAdapter);
        recyclerView = view.findViewById(R.id.listDisplayUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        mGridView = view.findViewById(R.id.freelancer_gridview);



        return view;
    }

   /* private void setDisplayUsers(View v){

        recyclerView = v.findViewById(R.id.listDisplayUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    userModel = dataSnapshot.getValue(FreelancerModel.class);
                    if(! userModel.getId().equals(user.getUid())){
                        mUsers.add(userModel);
                    }
                }
                adapter= new UserAdapter(getContext(), mUsers,false);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/
    public void insertUserToDatabase(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //UserModel iUser = new UserModel(user.getUid(), user.getPhotoUrl().toString(), user.getDisplayName(), "offline");
        // iUser.InsertUsers(iUser);

    }
    public void getUserMatchingtheSearchTerm(final String tag){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tags").child(tag);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if( !dataSnapshot.getKey().equals("tag")){
                        DatabaseReference refUser = FirebaseDatabase.getInstance().getReference("Users")
                                .child(dataSnapshot.getKey());
                        refUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                FreelancerModel userModel1=snapshot.getValue(FreelancerModel.class);
                                mUsers.add(userModel1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                adapterGrid= new FreelancerListAdapter(getContext(), mUsers);
                adapterGrid.notifyDataSetChanged();


                mGridView.setAdapter(adapterGrid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void getListTags(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tags");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                autoCompleteListe.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    autoCompleteListe.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}