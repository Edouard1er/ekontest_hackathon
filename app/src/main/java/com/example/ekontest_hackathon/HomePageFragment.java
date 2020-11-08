package com.example.ekontest_hackathon;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HomePageFragment extends Fragment {
    ListView mListView;
    CustomHomePageAdapter mAdapter;
    ArrayList mArrayList;
    ViewPager2 mathViewPager2,  topViewPager, francaisViewPager2, cSharpViewPager2, javaViewPager2;
    List<SwipeUser> mUserListMath,mUserListChimie, mUserListFrancais, mUserListCSharp, mUserListJava;
    SwipeUserAdapter mSwipeUserAdapterMath, mSwipeFreelancerAdapter, mSwipeUserAdapterFrancais, mSwipeUserAdapterCSharp, mSwipeUserAdapterJava;


    SwipeUserAdapter adapter;
    List<FreelancerModel> mFreelancers = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home_page, container, false);
        mUserListMath = new ArrayList<>();
        mUserListFrancais = new ArrayList<>();
        mUserListChimie = new ArrayList<>();
        mUserListCSharp = new ArrayList<>();
        mUserListJava = new ArrayList<>();
        //Math
        mathViewPager2=view.findViewById(R.id.id_math_viewPager2);
        mUserListMath.add(new SwipeUser("Edouard Chevenslove", "10",2,R.drawable.person));
        mUserListMath.add(new SwipeUser("Osson Sergio", "5",4,R.drawable.luka2));
        mUserListMath.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        //mSwipeUserAdapterMath = new SwipeUserAdapter(getContext(),mUserListMath);
        mathViewPager2.setAdapter(mSwipeUserAdapterMath);
       /* //Chimie
        chimieViewPager2=view.findViewById(R.id.id_chimie_viewPager2);
        mUserListChimie.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListChimie.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListChimie.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mSwipeUserAdapterChime = new SwipeUserAdapter(getContext(),mUserListChimie);
        chimieViewPager2.setAdapter(mSwipeUserAdapterChime);
        //Francais
        francaisViewPager2=view.findViewById(R.id.id_francais_viewPager2);
        mUserListFrancais.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListFrancais.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListFrancais.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mSwipeUserAdapterFrancais = new SwipeUserAdapter(getContext(),mUserListFrancais);
        francaisViewPager2.setAdapter(mSwipeUserAdapterFrancais);
        //CSharp
        cSharpViewPager2=view.findViewById(R.id.id_CSharp_viewPager2);
        mUserListCSharp.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListCSharp.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListCSharp.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mSwipeUserAdapterCSharp = new SwipeUserAdapter(getContext(),mUserListCSharp);
        cSharpViewPager2.setAdapter(mSwipeUserAdapterCSharp);
        //Java
        javaViewPager2=view.findViewById(R.id.id_java_viewPager2);
        mUserListJava.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListJava.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mUserListJava.add(new SwipeUser("Dorceus Amos", "5",4,R.drawable.messi));
        mSwipeUserAdapterJava = new SwipeUserAdapter(getContext(),mUserListJava);
        javaViewPager2.setAdapter(mSwipeUserAdapterJava);*/

        setDisplayUsers(view);
        return view;
    }

    private void setDisplayUsers(View v){
        topViewPager=v.findViewById(R.id.id_math_viewPager2);
        //recyclerView.setHasFixedSize(true);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference user_ = FirebaseDatabase.getInstance().getReference("Users");
        user_.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    FreelancerModel model = dataSnapshot.getValue(FreelancerModel.class);
                    System.out.println(snapshot);
                    try {
                        if(model.getPersonalInformationModel().getType().equals("Freelancer")){
                            mFreelancers.add(model);
                            adapter.notifyDataSetChanged();
                        }
                        if(model.getPersonalInformationModel().getType().equals("Professor")){
                            mFreelancers.add(model);
                            adapter.notifyDataSetChanged();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                adapter= new SwipeUserAdapter(getContext(), mFreelancers);
                topViewPager.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        /*DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("myFreelancers");
        // Query query = freelancerRef.equalTo("personalInformationModel").equalTo("Student","type");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mFreelancers.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    System.out.println(dataSnapshot);
                    HashMap<String, String> obj = (HashMap<String, String>) snapshot.getValue();
                    System.out.println("id user__: " + obj);
                    DatabaseReference user = FirebaseDatabase.getInstance().getReference("Users").child( dataSnapshot.getKey());
                    user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FreelancerModel model = snapshot.getValue(FreelancerModel.class);
                            System.out.println(snapshot);
                            try {
                                if(model.getPersonalInformationModel().getType().equals("Freelancer")){
                                    mFreelancers.add(model);
                                    adapter.notifyDataSetChanged();
                                }
                                if(model.getPersonalInformationModel().getType().equals("Professor")){
                                    mFreelancers.add(model);
                                    adapter.notifyDataSetChanged();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                adapter= new SwipeUserAdapter(getContext(), mFreelancers);
                topViewPager.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
    }
}