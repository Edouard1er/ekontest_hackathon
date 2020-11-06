package com.example.ekontest_hackathon;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
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



public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
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

    RecyclerView recyclerView;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    UserModel userModel;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> autoCompleteAdapter;
    ArrayList<String> autoCompleteListe ;
    ImageButton searchButton;


    GridView mGridView;
    List<FreelancerModel> mUsers=new ArrayList<>();
    FreelancerListAdapter adapterGrid;
    ListView listViewTag;
    List <TagModel> mTag= new ArrayList<>();
    TagListAdapter adapterTag ;

    ArrayAdapter simpleAdapter;
    List <String> simpleTag= new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_search, container, false);
        autoCompleteTextView =(AutoCompleteTextView) view.findViewById(R.id.search_freelancer);
        searchButton =(ImageButton) view.findViewById(R.id.searchResult);
        searchButton.setVisibility(View.GONE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "You click", Toast.LENGTH_SHORT).show();
                if(autoCompleteTextView.getText().toString().trim().length()!=0){
                    getUserMatchingtheSearchTerm(autoCompleteTextView.getText().toString());
                    hideKeybaord(v);

                }

            }
        });
        autoCompleteListe =new ArrayList<>();
        listViewTag= view.findViewById(R.id.list_tag);
        listViewTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tag = listViewTag.getItemAtPosition(position).toString().trim();
              /*  String [] word=val.split(" ");
                String capitalizeEachWord="";
                for(int i=0 ; i<word.length; i++){
                    try{
                        capitalizeEachWord = capitalizeEachWord+" "+word[i].substring(0, 1).toUpperCase() + word[i].substring(1).toLowerCase();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }*/
                //String tag = capitalizeEachWord;
                //Toast.makeText(getContext(), ""+capitalizeEachWord, Toast.LENGTH_SHORT).show();
                //Toast.makeText(getContext(), ""+val, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),SearchResultActivity.class);
                intent.putExtra("tag", tag);
                startActivity(intent);
            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(s.toString().trim().length()!=0){
                    searchButton.setVisibility(View.VISIBLE);
                }else{
                    searchButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().trim().length()!=0){
                    searchButton.setVisibility(View.VISIBLE);
                }else{
                    searchButton.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()!=0){
                    searchButton.setVisibility(View.VISIBLE);
                }else{
                    searchButton.setVisibility(View.GONE);
                }
            }
        });

       /* ArrayList<String> a =new ArrayList<>();
        a.add("Amos");
        a.add("Jodler");*/
        TagModel tagModel = new TagModel();
        /*tagModel.InsertTag("Histoire Moderne");
        tagModel.InsertTag("Chimie Organique");
        tagModel.InsertTag("Mathematique Financiere");*/


        getListTags();
        showListTag();
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
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tags").child(tag);
        
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    if(snapshot.getChildrenCount()>1){
                        Intent intent = new Intent(getContext(),SearchResultActivity.class);
                        intent.putExtra("tag", tag);
                        startActivity(intent);
                    }else{
                        if(snapshot.getChildrenCount()==1){
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if(!dataSnapshot.getKey().equals(user.getUid())){
                                    Intent intent = new Intent(getContext(),SearchResultActivity.class);
                                    intent.putExtra("tag", tag);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(getContext(), "No User matching this search", Toast.LENGTH_SHORT).show();
                                }

                            }
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Tags").child(tag);


                        }
                    }
                }else{
                    Toast.makeText(getContext(), "No User matching this search", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        
        
        /*ref.addValueEventListener(new ValueEventListener() {
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
                                if(!userModel1.getId().equals(user.getUid())){
                                    mUsers.add(userModel1);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
                adapterGrid= new FreelancerListAdapter(getContext(), mUsers);
                //adapterGrid.notifyDataSetChanged();


                mGridView.setAdapter(adapterGrid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/


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
    public void showListTag(){

      /*  listViewTag.setHasFixedSize(true);
        listViewTag.setLayoutManager(new LinearLayoutManager(getContext()));*/
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    DatabaseReference databaseReference2 =  FirebaseDatabase.getInstance().getReference("Tags");
                    databaseReference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                final String tag = dataSnapshot.getKey();
                                DatabaseReference databaseReference3 =  FirebaseDatabase.getInstance()
                                        .getReference("Tags")
                                        .child(dataSnapshot.getKey());
                                databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                           final  Long qte= dataSnapshot1.getChildrenCount();
                                            final String userKey=dataSnapshot1.getKey();

                                                    DatabaseReference databaseReference4 =  FirebaseDatabase.getInstance()
                                                    .getReference("Users").child(dataSnapshot1.getKey());
                                            databaseReference4.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){
                                                        UserModel model = snapshot.getValue(UserModel.class);
                                                        String name=model.getPersonalInformationModel().getFirstname()+" "+model.getPersonalInformationModel().getLastname();
                                                      // Toast.makeText(getContext(), ""+name, Toast.LENGTH_SHORT).show();
                                                        if(!(name).equals(tag)){
                                                            if(!user.getUid().equals(userKey)){
                                                                try{
                                                                    if(!simpleTag.contains(tag)){
                                                                        simpleTag.add(tag);
                                                                        simpleAdapter= new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,simpleTag);
                                                                        listViewTag.setAdapter(simpleAdapter);
                                                                     /*TagModel m = new TagModel();
                                                                m.setTag(tag);
                                                                mTag.add(m);

                                                                adapterTag= new TagListAdapter(getContext(), mTag);
                                                                 listViewTag.setAdapter(adapterTag);*/

                                                                    }
                                                                }catch (Exception e){

                                                                }





                                                            }

                                                        }else{
                                                            //Toast.makeText(getContext(), "Never inside", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
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

                            }
                          /*
                            if(snapshot.exists() && !tag.equals(nom.getText().toString()+" "+prenom.getText().toString())){
                                TagModel model = new TagModel();
                                model.setTag(tag);
                                mTag.add(model);
                                listViewTag.setHasFixedSize(true);
                                listViewTag.setLayoutManager(new LinearLayoutManager(getContext()));

                               ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, autoCompleteListe);


                                adapterTag= new TagListAdapter(getContext(), mTag);
                                listViewTag.setAdapter(adapterTag);

                                //Toast.makeText(AccountActivity.this, ""+model.getTag(), Toast.LENGTH_SHORT).show();
                            }
                        */
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