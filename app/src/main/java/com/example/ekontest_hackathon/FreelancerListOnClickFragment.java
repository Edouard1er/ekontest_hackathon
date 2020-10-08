package com.example.ekontest_hackathon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekontest_hackathon.ui.NavDrawerActivity;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;


public class FreelancerListOnClickFragment extends Fragment {
    SimpleRatingBar mRatingBar;
    ListView mListView;
    ArrayList mArrayList;
    InfoAcademicAdapter mAdapter;
    TextView redigerAvis, avisDisplayFreelancer, mFirstname, mLastname, mSexe;
    onClickInfoFreelancer listener;
    Bundle results;
    ImageView mImageFreelancer;
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
        FreelancerOnclickActivity activity = (FreelancerOnclickActivity)getActivity();
        results = activity.getFreelancerData();
        String value = results.getString("firstname");
        Toast.makeText(activity, value, Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_freelancer_list_on_click, container, false);
        mRatingBar = view.findViewById(R.id.id_rating_bar_freelancer);
        mRatingBar.setStarsSeparation(100,30);
        mRatingBar.setFillColor(R.color.bleu_fonce);
        mRatingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.redigerAvis();
            }
        });
        mArrayList = new ArrayList<InfoAcademicModel>();
        mListView= view.findViewById(R.id.list_info_academic_freelancer);
        mArrayList.add(new InfoAcademicModel(1, "ESIH", "Sces Info", "Licence", "2016", "2020"));
        mAdapter = new InfoAcademicAdapter(getContext(), R.layout.info_academic_custom_listview,mArrayList);
        mListView.setAdapter(mAdapter);
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
        mFirstname = view.findViewById(R.id.prenom_freelancer);
        mLastname = view.findViewById(R.id.nom_freelancer);
        mSexe = view.findViewById(R.id.sexe_freelancer);
        mImageFreelancer = view.findViewById(R.id.image_freelancer);

        setInfoPerso();
        return view;
    }

    public void setInfoPerso(){
        mFirstname.setText(results.getString("firstname"));
        mLastname.setText(results.getString("lastname"));
        mSexe.setText(results.getString("sexe"));
        final String[] url = new String[2];
        url[0] = results.getString("imagelink");
        url[1] = results.getString("imagename");
        UserAdapter userAdapter = new UserAdapter();
        userAdapter.getUrlImage(url, mImageFreelancer);




    }


    public interface onClickInfoFreelancer{
         void redigerAvis();
         void allAvis();
    }
}