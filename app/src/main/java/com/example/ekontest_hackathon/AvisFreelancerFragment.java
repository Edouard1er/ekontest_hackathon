package com.example.ekontest_hackathon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvisFreelancerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvisFreelancerFragment extends Fragment implements InterfaceAvis {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AvisFreelancerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AvisFreelancerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AvisFreelancerFragment newInstance(String param1, String param2) {
        AvisFreelancerFragment fragment = new AvisFreelancerFragment();
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
    AvisFreelancerActivity activity;
    Bundle results;
    String idFreelancer;
    SimpleRatingBar mRatingBar;
    EditText mComment;
    Button sendAvis;
    ArrayList<FreelancerModel> userModels;
    UserModel singleUser= new UserModel();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avis_freelancer, container, false);

        activity = (AvisFreelancerActivity) getActivity();
       /* results = activity.getFreelancerId();
        idFreelancer=results.getString("freelancer");*/
        results = activity.getFreelancerData();
        userModels=results.getParcelableArrayList("freelancer");
        singleUser=userModels.get(0);
        mRatingBar = view.findViewById(R.id.avis_rating_bar);
        mComment = view.findViewById(R.id.avis_comment);
        sendAvis= view.findViewById(R.id.send_comment_avis_freelancer);

        sendAvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertAvis(v);

            }
        });

        Toast.makeText(activity, "Yes i get the id of the freelancer"+ idFreelancer, Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return view;
    }
    public void InsertAvis(View v){
        AvisModel avisModel = new AvisModel();
        avisModel.InsertAvis(singleUser.getId(),mComment.getText().toString(),(int)mRatingBar.getRating());
        Intent intent = new Intent(v.getContext(), FreelancerOnclickActivity.class);
        ArrayList<UserModel> userArray= new ArrayList<>();
        userArray.add(singleUser);
        intent.putParcelableArrayListExtra("freelancer",userArray);
        v.getContext().startActivity(intent);

    }



    public SimpleRatingBar getmRatingBar() {
        return mRatingBar;
    }

    public EditText getmComment() {
        return mComment;
    }

    @Override
    public void InsertAvis(String id, String comment, int rate) {
        AvisModel avisModel = new AvisModel();
        avisModel.InsertAvis(id,comment,rate);
    }


}