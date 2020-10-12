package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AcademicInformationAdapter extends RecyclerView.Adapter <AcademicInformationAdapter.ViewHolder> {
    private Context context;
    List<AcademicInformationModel> mAcademic;

    public AcademicInformationAdapter(Context context, List <AcademicInformationModel> mAcademic) {
        this.context=context;
        this.mAcademic=mAcademic;
    }

    public AcademicInformationAdapter (){
        //Empty constructor
    }
    @NonNull
    @Override
    public AcademicInformationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.info_academic_item, parent, false);
        return new AcademicInformationAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AcademicInformationAdapter.ViewHolder holder, int position) {
        AcademicInformationModel academicModel=mAcademic.get(position);
        int val =position+1;
        holder.nFormation.setText("Formation "+val);
        holder.level.setText(academicModel.getLevel());
        holder.institution.setText(academicModel.getInstitution());
        holder.faculte.setText(academicModel.getFaculte());
        holder.degree.setText(academicModel.getDegree());
        holder.startDate.setText(academicModel.getStartDate());
        holder.endDate.setText(academicModel.getEndDate());
    }
    @Override
    public int getItemCount() { return mAcademic.size();}
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{

        TextView nFormation,level,institution , faculte,degree,startDate,endDate ;

        public  ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            context = itemView.getContext();

            nFormation=itemView.findViewById(R.id.which_formation);
            level=itemView.findViewById(R.id.tv_level_of_study);
            institution=itemView.findViewById(R.id.tv_institution);
            faculte = itemView.findViewById(R.id.tv_faculty);
            degree= itemView.findViewById(R.id.tv_degree);
            startDate=itemView.findViewById(R.id.tv_start_year);
            endDate=itemView.findViewById(R.id.tv_end_year);

        }
        @Override
        public void onClick(View v) {


        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }


}

