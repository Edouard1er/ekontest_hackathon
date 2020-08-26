package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        UserAdapter userAdapter= new UserAdapter();
        userAdapter.getUrlImage(model.getPersonalInformationModel().getImagename(),imageFreelancer);
        nameFreelancer.setText(model.getPersonalInformationModel().getFirstname()+" "+model.getPersonalInformationModel().getLastname());

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,model.getPersonalInformationModel().getFirstname(),Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}
