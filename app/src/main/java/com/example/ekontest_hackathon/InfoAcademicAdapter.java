package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;




class InfoAcademicAdapter extends ArrayAdapter {
    Context mContext;
    ArrayList<InfoAcademicModel> mArrayList;

    public InfoAcademicAdapter(@NonNull Context context, int resource, ArrayList<InfoAcademicModel> mArrayList) {
        super(context, resource, mArrayList);
        this.mContext=context;
        this.mArrayList=mArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater)mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        convertView= layoutInflater.inflate(R.layout.info_academic_custom_listview, parent, false);

        TextView ecole = (TextView)convertView.findViewById(R.id.ecole_id_info);
        TextView filiere = (TextView)convertView.findViewById(R.id.filiere_id_info);
        TextView etudeNumber= convertView.findViewById(R.id.etude_number__id_info);
        TextView diplome= convertView.findViewById(R.id.diplome_id_info);
        TextView startYear= convertView.findViewById(R.id.start_year_id_info);
        TextView endYear= convertView.findViewById(R.id.end_year_id_info);

        ecole.setText(mArrayList.get(position).getEcole());
        filiere.setText(mArrayList.get(position).getFiliere());
        diplome.setText(mArrayList.get(position).getDiplome());

        etudeNumber.setText(""+ mArrayList.get(position).getLevel());
        startYear.setText(mArrayList.get(position).getStartYear());
        endYear.setText(mArrayList.get(position).getEndYear());
        return convertView;

    }
}

