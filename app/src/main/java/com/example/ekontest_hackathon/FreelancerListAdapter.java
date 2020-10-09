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
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class FreelancerListAdapter extends BaseAdapter {
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

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,model.getPersonalInformationModel().getFirstname(),Toast.LENGTH_SHORT).show();
                String nom = model.getPersonalInformationModel().getFirstname();
                Intent intent = new Intent(v.getContext(), FreelancerOnclickActivity.class);
                intent.putExtra("firstname", model.getPersonalInformationModel().getFirstname());
                intent.putExtra("lastname", model.getPersonalInformationModel().getLastname());
                intent.putExtra("sexe", model.getPersonalInformationModel().getSexe());
                intent.putExtra("imagelink", model.getPersonalInformationModel().getImagelink());
                intent.putExtra("imagename", model.getPersonalInformationModel().getImagename());


                // intent.putExtra("model",(Parcelable) model);
                v.getContext().startActivity(intent);

            }
        });

        return convertView;
    }

}
