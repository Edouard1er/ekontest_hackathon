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

public class StudentListAdapter extends BaseAdapter implements SearchView.OnQueryTextListener, Filterable {
    private  Context context;
    private Boolean isChat;
    List<StudentModel> mStudent;
    List<StudentModel> userFiltered;
    CustomFilter filter;

    public StudentListAdapter(Context context, List <StudentModel> mStudent){
        this.context =context;
        this.mStudent = mStudent;
        this.userFiltered=mStudent;

    }

    @Override
    public int getCount() {
        return mStudent.size();
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
            convertView= LayoutInflater.from(context).inflate(R.layout.row_student_item,parent,false);
        }

        ImageView imageFreelancer= convertView.findViewById(R.id.image_student);
        TextView nameFreelancer= (TextView) convertView.findViewById(R.id.name_student);





        final StudentModel model=  mStudent.get(position);
        final String[] url = new String[2];
        try {
            url[0] = model.getPersonalInformationModel().getImagelink();
            url[1] = model.getPersonalInformationModel().getImagename();
            UserAdapter userAdapter= new UserAdapter();
            //   userAdapter.getUrlImage(model.getPersonalInformationModel().getImagelink(),imageFreelancer);
            userAdapter.getUrlImage(url,imageFreelancer);
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            String firstname=model.getPersonalInformationModel().getFirstname().toLowerCase();
            firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1);

            String lastname=model.getPersonalInformationModel().getLastname().toLowerCase();
            lastname = lastname.substring(0,1).toUpperCase() + lastname.substring(1);
            nameFreelancer.setText(firstname+" "+lastname);
        }catch (Exception e){
            e.printStackTrace();
        }



        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent intent = new Intent(v.getContext(), FreelancerOnclickActivity.class);
                ArrayList<StudentModel> userArray= new ArrayList<>();
                userArray.add(model);
                intent.putParcelableArrayListExtra("freelancer",userArray);
                // intent.putExtra("model",(Parcelable) model);
                v.getContext().startActivity(intent);*/

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

                ArrayList<StudentModel> filters=new ArrayList<StudentModel>();
                // filters.clear();
                //get specific items
                for(StudentModel user : userFiltered)
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



            mStudent=(ArrayList<StudentModel>) results.values;
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