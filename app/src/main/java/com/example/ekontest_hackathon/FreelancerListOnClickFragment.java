package com.example.ekontest_hackathon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
            mLastname, mSexe, mFreeDocument,mPaidDocument,mEtudiantFreelancer, mRatingValue, totalAvis;
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
    LinearLayout altUserImage;
    TextView altTxtName;

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

        FreelancerModel m = new FreelancerModel();
        m.addFreelancerStudent(singleUser.getId());

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_freelancer_list_on_click, container, false);
        mRatingBar = view.findViewById(R.id.id_rating_bar_freelancer);
        mRatingBar2 = view.findViewById(R.id.rating_bar_item_freelancer2);
        mRatingValue = view.findViewById(R.id.rating_bar_value_freelancer);
        mRatingBar.setStarsSeparation(100,30);
        mRatingBar.setFillColor(R.color.couleur3);
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
     /*   if(mAvis.size()<1){
            avisDisplayFreelancer.setVisibility(View.GONE);
        }else{
            avisDisplayFreelancer.setVisibility(View.VISIBLE);
        }*/
        avisDisplayFreelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listener.allAvis();
                Intent intent = new Intent(getContext(), AvisDisplayAllActivity.class);
                intent.putExtra("idUser",singleUser.getId());
                // intent.putExtra("model",(Parcelable) model);
                v.getContext().startActivity(intent);
            }
        });

        //getting and setting info dynamically
        avisModel = new AvisModel();
        progressBar1= view.findViewById(R.id.progressBar1);
        progressBar2= view.findViewById(R.id.progressBar2);
        progressBar3= view.findViewById(R.id.progressBar3);
        progressBar4= view.findViewById(R.id.progressBar4);
        progressBar5= view.findViewById(R.id.progressBar5);


         altUserImage=view.findViewById(R.id.altImage);
         altTxtName=view.findViewById(R.id.altTextName);
        mFirstname = view.findViewById(R.id.prenom_freelancer);
        mLastname = view.findViewById(R.id.nom_freelancer);
        mSexe = view.findViewById(R.id.sexe_freelancer);
        mImageFreelancer = view.findViewById(R.id.image_freelancer);
        mPaidDocument = view.findViewById(R.id.paid_document);
        mFreeDocument = view.findViewById(R.id.free_document);

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
         DocumentModel model = new DocumentModel();
       //  model.InsertDocument("Fiscalite",20);
        //model.InsertDocument("OTI",20);

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
            urlImageModel.getUrlImage(url, mImageFreelancer,getContext(), altUserImage,altTxtName,singleUser);

        }catch (Exception e){
            System.out.println("There is something wrong in freelancerlistOnclickFragment setinfoperson method");
        }

    }

    public void setInfoProfil(){

        try {
            ProfilModel model = new ProfilModel();
            model.setProfilModel(singleUser.getId(), mFreeDocument,mPaidDocument,mEtudiantFreelancer);
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

                ArrayList<AvisModel> mAvis2= new ArrayList<>();
                try{

                    mAvis2.add(mAvis.get(0));

                }catch (Exception e){

                }
                try{
                    mAvis2.add(mAvis.get(1));


                }catch (Exception e){

                }
                try{
                    mAvis2.add(mAvis.get(2));

                }catch (Exception e){

                }
                try{
                    mAvis2.add(mAvis.get(3));


                }catch (Exception e){

                }


                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter=new AvisAdapter(getContext(), mAvis2);
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
                academicInformationAdapter=new AcademicInformationAdapter(getContext(), mAcademics,false);
                academicRecyclerView.setAdapter(academicInformationAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void goToSeeAllAvis(View view){

    }

    public interface onClickInfoFreelancer{
        void redigerAvis();
        void allAvis();
    }
}