package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.ArrayList;

public class AvisFreelancerActivity extends AppCompatActivity {
    AvisFreelancerFragment avisFreelancerFragment;
    Button sendAvis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avis_freelancer);
        avisFreelancerFragment=new AvisFreelancerFragment();
        sendAvis = findViewById(R.id.send_avis_freelancer);
        sendAvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList <FreelancerModel> userModels;
                userModels=getIntent().getParcelableArrayListExtra("freelancer");
                View view = avisFreelancerFragment.getView();
               SimpleRatingBar mRatingbar = view.findViewById(R.id.avis_rating_bar);
                EditText mComment = view.findViewById(R.id.avis_comment);

                avisFreelancerFragment.InsertAvis(userModels.get(0).getId(), mComment.getText().toString(), (int) mRatingbar.getRating());
                Intent intent = new Intent(v.getContext(), FreelancerOnclickActivity.class);
                intent.putParcelableArrayListExtra("freelancer",userModels);
                startActivity(intent);
                finish();
            }
        });
    }
    public Bundle getFreelancerId(){

        // Toast.makeText(this, "Trying something :"+userModels.get(0).getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();
        Bundle fd = new Bundle();
        fd.putString("freelancer", getIntent().getStringExtra("freelancer"));

        return fd;
    }
    public Bundle getFreelancerData(){
        ArrayList <FreelancerModel> userModels;
        userModels=getIntent().getParcelableArrayListExtra("freelancer");
        // Toast.makeText(this, "Trying something :"+userModels.get(0).getPersonalInformationModel().getLastname(), Toast.LENGTH_SHORT).show();

        Bundle fd = new Bundle();
        fd.putParcelableArrayList("freelancer", userModels);

        return fd;
    }
}