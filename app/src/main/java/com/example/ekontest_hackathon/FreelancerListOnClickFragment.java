package com.example.ekontest_hackathon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;
import java.util.List;


public class FreelancerListOnClickFragment extends Fragment{
    FreelancerOnclickActivity activity;
    Bundle results;
    SimpleRatingBar mRatingBar;
    ListView mListView;
    ArrayList mArrayList;
    InfoAcademicAdapter mAdapter;
    TextView redigerAvis, avisDisplayFreelancer, mFirstname,
            mLastname, mSexe, mCoursFreelancer,mEtudiantFreelancer, mRatingValue, totalAvis;
    ProgressBar progressBar1,progressBar2, progressBar3, progressBar4, progressBar5;
    RatingBar mRatingBar2;
    onClickInfoFreelancer listener;
    LinearLayout rateLayout;
    ImageView mImageFreelancer;

    AvisAdapter adapter;
    List<AvisModel> mAvis=new ArrayList<>();
    List<AcademicInformationModel> mAcademics=new ArrayList<>();
    AcademicInformationAdapter academicInformationAdapter;

    RecyclerView recyclerView;
    RecyclerView academicRecyclerView;
    ArrayList <FreelancerModel> userModels;
    FreelancerModel singleUser;
    FloatingActionButton sendFreelancerMessage;

    // Important when you have a listener with an interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof onClickInfoFreelancer){
            listener = (onClickInfoFreelancer) context;
        }else {
            //throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //getting data from activity
        activity = (FreelancerOnclickActivity) getActivity();
        try{
            results = activity.getFreelancerData();
            userModels=results.getParcelableArrayList("freelancer");
            singleUser=userModels.get(0);
        }catch (Exception e){
            e.printStackTrace();
        }



        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_freelancer_list_on_click, container, false);
        mRatingBar = view.findViewById(R.id.id_rating_bar_freelancer);
        mRatingBar2 = view.findViewById(R.id.rating_bar_item_freelancer2);
        mRatingValue = view.findViewById(R.id.rating_bar_value_freelancer);
        mRatingBar.setStarsSeparation(100,30);
        mRatingBar.setFillColor(R.color.bleu_fonce);
        rateLayout=view.findViewById(R.id.rate_layout);

        //Make rate layout invisible or visible
        try{
            MyFreelancerModel myFreelancerModel = new MyFreelancerModel();
            // myFreelancerModel.InsertMyFreelancer(singleUser.getId());
            myFreelancerModel.MakeRatingOptionVisible(singleUser.getId(),rateLayout);
        }catch (Exception e){
            e.printStackTrace();
        }


        //Sending message to Freelancer
        sendFreelancerMessage= view.findViewById(R.id.send_freelancer_message);
        sendFreelancerMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "You want to send message to: "+ singleUser.getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();
                try {
                    Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
                    intent.putExtra("receiver", singleUser.getId());
                    //Toast.makeText(DisplayUser.this, receiverId.getText().toString(), Toast.LENGTH_SHORT).show();

                    v.getContext().startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        AvisModel avisModel;


        mRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.redigerAvis();
                //Toast.makeText(activity, "You want to rate: "+ singleUser.getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();
               try{
                   Intent intent = new Intent(v.getContext(), AvisFreelancerActivity.class);
                   // intent.putExtra("freelancer", singleUser.getId());
                   intent.putParcelableArrayListExtra("freelancer", userModels);
                   v.getContext().startActivity(intent);
               }catch (Exception e){
                   e.printStackTrace();
               }
            }
        });
        mArrayList = new ArrayList<InfoAcademicModel>();
        //mListView= view.findViewById(R.id.list_info_academic_freelancer);
        mArrayList.add(new InfoAcademicModel("University", "ESIH", "Sces Info", "Licence", "2016", "2020"));
        mAdapter = new InfoAcademicAdapter(getContext(), R.layout.info_academic_custom_listview,mArrayList);
//        mListView.setAdapter(mAdapter);
        redigerAvis = view.findViewById(R.id.id_rediger_avis_freelancer);
        redigerAvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.redigerAvis();
            }
        });
        avisDisplayFreelancer= view.findViewById(R.id.avisDisplayFreelancer);
        avisDisplayFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.allAvis();
            }
        });

        //getting and setting info dynamically
        avisModel = new AvisModel();
        progressBar1= view.findViewById(R.id.progressBar1);
        progressBar2= view.findViewById(R.id.progressBar2);
        progressBar3= view.findViewById(R.id.progressBar3);
        progressBar4= view.findViewById(R.id.progressBar4);
        progressBar5= view.findViewById(R.id.progressBar5);

        mFirstname = view.findViewById(R.id.prenom_freelancer);
        mLastname = view.findViewById(R.id.nom_freelancer);
        mSexe = view.findViewById(R.id.sexe_freelancer);
        mImageFreelancer = view.findViewById(R.id.image_freelancer);
        mCoursFreelancer = view.findViewById(R.id.cours_freelancer);
        mEtudiantFreelancer = view.findViewById(R.id.etudiants_freelancer);
        recyclerView = view.findViewById(R.id.list_avis_on_click);
        academicRecyclerView= view.findViewById(R.id.list_info_academic_freelancer);
        totalAvis = view.findViewById(R.id.avis_total);
        setInfoPerso();
        setInfoProfil();
        try {
            avisModel.setInfoAvis(singleUser.getId(), mRatingBar);
            avisModel.setInfoAvis(singleUser.getId(), mRatingBar2, mRatingValue, progressBar1, progressBar2,
                    progressBar3, progressBar4, progressBar5,totalAvis);
        }catch (Exception e){
            System.out.println("There is something wrong in freelancerlistOnclickFragment oncreateView method");

        }


        adapter = new AvisAdapter();
        academicInformationAdapter= new AcademicInformationAdapter();
        readAvis(view);
        readAcademicInfo(view);
        return view;
    }

    public void setInfoPerso(){
        try {
            mFirstname.setText(singleUser.getPersonalInformationModel().getFirstname());
            mLastname.setText(singleUser.getPersonalInformationModel().getLastname());
            mSexe.setText(singleUser.getPersonalInformationModel().getSexe());
            final String[] url = new String[2];
            url[0] = singleUser.getPersonalInformationModel().getImagelink();
            url[1] = singleUser.getPersonalInformationModel().getImagename();
            UrlImageModel urlImageModel = new UrlImageModel();
            urlImageModel.getUrlImage(url, mImageFreelancer);
        }catch (Exception e){
            System.out.println("There is something wrong in freelancerlistOnclickFragment setinfoperson method");
        }

    }

    public void setInfoProfil(){

        try {
            mCoursFreelancer.setText(""+singleUser.getProfilModel().getnCours());
            mEtudiantFreelancer.setText(""+singleUser.getProfilModel().getnEtudiant());
        }catch (Exception e){
            System.out.println("There is something wrong in freelancerlistOnclickFragment setinfoprofil method");

        }

    }

    private void readAvis(final View v) {
        DatabaseReference avisRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(singleUser.getId())
                .child("avisModel");
        avisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                mAvis.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AvisModel avis = dataSnapshot.getValue(AvisModel.class);
                    mAvis.add(avis);
                    //System.out.println("This is the text message :"+ avis.getComment());
                   // System.out.println("This is the text message :"+ mAvis.size());

                }


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter=new AvisAdapter(getContext(), mAvis);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readAcademicInfo(final View v) {

        DatabaseReference avisRef = FirebaseDatabase.getInstance().getReference("Users")
                .child(singleUser.getId())
                .child("academicInformationModel");
        avisRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                mAcademics.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    AcademicInformationModel academic = dataSnapshot.getValue(AcademicInformationModel.class);
                    mAcademics.add(academic);


                }


                academicRecyclerView.setHasFixedSize(true);
                academicRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                academicInformationAdapter=new AcademicInformationAdapter(getContext(), mAcademics);
                academicRecyclerView.setAdapter(academicInformationAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public interface onClickInfoFreelancer{
        void redigerAvis();
        void allAvis();
    }
}