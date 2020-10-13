package com.example.ekontest_hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.$Gson$Preconditions;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FreelancerListAdapter extends BaseAdapter implements SearchView.OnQueryTextListener {
    private  Context context;
    private Boolean isChat;
    List<UserModel> mFreelancer;

    public FreelancerListAdapter(Context context, List <UserModel> mFreelancer){
        this.context =context;
        this.mFreelancer = mFreelancer;

    }

    @Override
    public int getCount() {
        return mFreelancer.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.row_item_freelancer,parent,false);
        }

        ImageView imageFreelancer= convertView.findViewById(R.id.image_freelancer);
        TextView nameFreelancer= (TextView) convertView.findViewById(R.id.name_freelancer);
        RatingBar mRatingBar=convertView.findViewById(R.id.rating_bar_item_freelancer);
        TextView nbrCoursFreelancer= (TextView) convertView.findViewById(R.id.nbr_cours_freelancer);
        TextView nbrEtudiantFreelancer= (TextView) convertView.findViewById(R.id.nbr_etudiant_freelancer);



        final UserModel model= (UserModel) mFreelancer.get(position);
        final String[] url = new String[2];
        url[0] = model.getPersonalInformationModel().getImagelink();
        url[1] = model.getPersonalInformationModel().getImagename();

        UserAdapter userAdapter= new UserAdapter();
     //   userAdapter.getUrlImage(model.getPersonalInformationModel().getImagelink(),imageFreelancer);
        userAdapter.getUrlImage(url,imageFreelancer);
        nameFreelancer.setText(model.getPersonalInformationModel().getFirstname()+" "+model.getPersonalInformationModel().getLastname());
        try {
            nbrCoursFreelancer.setText("Cours : "+model.getProfilModel().getnCours());
            nbrEtudiantFreelancer.setText("Etudiants : "+model.getProfilModel().getnEtudiant());
        }catch (Exception e){

        }


        AvisModel fl = new AvisModel();
        fl.setInfoAvis(model.getId(), mRatingBar);
        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,model.getPersonalInformationModel().getFirstname(),Toast.LENGTH_SHORT).show();
                String nom = model.getPersonalInformationModel().getFirstname();
                //Intent intent = new Intent(v.getContext(), FreelancerOnclickActivity.class);
                Intent intent = new Intent(v.getContext(), FreelancerOnclickActivity.class);
                ArrayList<UserModel> userArray= new ArrayList<>();
                userArray.add(model);
                intent.putParcelableArrayListExtra("freelancer",userArray);
                // intent.putExtra("model",(Parcelable) model);
                v.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
