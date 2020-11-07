package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AvisAdapter extends RecyclerView.Adapter <AvisAdapter.ViewHolder> {
    private  Context context;
    List <AvisModel> mAvis;

    public AvisAdapter(Context context, List <AvisModel> mAvis) {
        this.context=context;
        this.mAvis=mAvis;
    }

    public AvisAdapter (){
        //Empty constructor
    }
    @NonNull
    @Override
    public AvisAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_item_avis, parent, false);
        return new AvisAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AvisAdapter.ViewHolder holder, int position) {
        AvisModel avisModel=mAvis.get(position);

        try{
            //Setting image and name of User
            UserModel userModel = new UserModel();
            userModel.getUserNameAndImage(avisModel.getIdUser(),holder.userImage,holder.userName,context,holder.altUserImage, holder.altTxtName);


            // holder.userName.setText("Amos Dorceus");
            holder.comment.setText(avisModel.getComment());
            holder.ratingBar.setRating(avisModel.getnStar());
            //Setting the date of the comment
            ConvertDateTimeModel convertDateTimeModel = new ConvertDateTimeModel();
            holder.date.setText( convertDateTimeModel.getDateWithOutTime(avisModel.getDatetime()));

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    @Override
    public int getItemCount() { return mAvis.size();}
    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener{
        TextView userName, date, comment,altTxtName;
        RatingBar ratingBar;
        ImageView userImage;
        ConstraintLayout altUserImage;
        public  ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            context = itemView.getContext();


            userImage=itemView.findViewById(R.id.avis_user_image);
            userName=itemView.findViewById(R.id.avis_user_name);
            date = itemView.findViewById(R.id.avis_date);
            comment= itemView.findViewById(R.id.avis_info_freelancer);
            ratingBar=itemView.findViewById(R.id.avis_user_rate);
            altUserImage=itemView.findViewById(R.id.altUserImage);
            altTxtName=itemView.findViewById(R.id.altTxtName);
        }
        @Override
        public void onClick(View v) {
           /* TextView receiverId = (TextView) v.findViewById(R.id.UserId);

            Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
            intent.putExtra("receiver", receiverId.getText().toString());
            //Toast.makeText(DisplayUser.this, receiverId.getText().toString(), Toast.LENGTH_SHORT).show();
            v.getContext().startActivity(intent);
*/

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }


}


