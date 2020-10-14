package com.example.ekontest_hackathon;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
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

public class FreelancerListAdapter extends BaseAdapter implements SearchView.OnQueryTextListener, Filterable {
    private  Context context;
    private Boolean isChat;
    List<FreelancerModel> mFreelancer;
    List<FreelancerModel> userFiltered;
    CustomFilter filter;

    public FreelancerListAdapter(Context context, List <FreelancerModel> mFreelancer){
        this.context =context;
        this.mFreelancer = mFreelancer;
        this.userFiltered=mFreelancer;

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



        final FreelancerModel model=  mFreelancer.get(position);
        final String[] url = new String[2];
        url[0] = model.getPersonalInformationModel().getImagelink();
        url[1] = model.getPersonalInformationModel().getImagename();

        UserAdapter userAdapter= new UserAdapter();
        //   userAdapter.getUrlImage(model.getPersonalInformationModel().getImagelink(),imageFreelancer);
        userAdapter.getUrlImage(url,imageFreelancer);
        String firstname=model.getPersonalInformationModel().getFirstname().toLowerCase();
        firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1);

        String lastname=model.getPersonalInformationModel().getLastname().toLowerCase();
        lastname = lastname.substring(0,1).toUpperCase() + lastname.substring(1);
        nameFreelancer.setText(firstname+" "+lastname);
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
                ArrayList<FreelancerModel> userArray= new ArrayList<>();
                userArray.add(model);
                intent.putParcelableArrayListExtra("freelancer",userArray);
                // intent.putExtra("model",(Parcelable) model);
                v.getContext().startActivity(intent);

            }
        });

        return convertView;
    }
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }

        return filter;
    }
    class CustomFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub

            FilterResults results=new FilterResults();

            if(constraint != null && constraint.length()>0)
            {
                Log.e("Main"," data search: "+constraint.toString());

                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();

                ArrayList<FreelancerModel> filters=new ArrayList<FreelancerModel>();
                // filters.clear();
                //get specific items
                for(FreelancerModel user : userFiltered)
                {
                    if(user.getPersonalInformationModel().getFirstname().toUpperCase().contains(constraint) || user.getPersonalInformationModel().getLastname().toUpperCase().contains(constraint))
                    {
                        filters.add(user);
                    }
                }
                results.values=filters;
                results.count=filters.size();

            }else
            {    results.values=userFiltered;
                results.count=userFiltered.size();


            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub



            mFreelancer=(ArrayList<FreelancerModel>) results.values;
            notifyDataSetChanged();
        }

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
