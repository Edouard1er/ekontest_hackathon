package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

class SwipeUserAdapter extends RecyclerView.Adapter<SwipeUserAdapter.UserViewHolder> {
    Context mContext;
    List <FreelancerModel> mUserList;

    public SwipeUserAdapter(Context context, List<FreelancerModel> userList) {
        mContext = context;
        mUserList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.swipe_item, parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        FreelancerModel user= mUserList.get(position);
        holder.nomFreelancerHome.setText(user.getPersonalInformationModel().getLastname()+" "+user.getPersonalInformationModel().getFirstname());
        holder.numberRatingBarHome.setRating((float)user.getRate());
        holder.numberCoursHome.setText("5");
        final String[] url = new String[2];
        url[0] = user.getPersonalInformationModel().getImagelink();
        url[1] = user.getPersonalInformationModel().getImagename();
        UrlImageModel urlImageModel = new UrlImageModel();
        urlImageModel.getUrlImage(url, holder.icon, mContext,holder.altImage,holder.altTextName,user);
        //holder.icon.setImageResource(user.getIcon());
        holder.page_total.setText(""+mUserList.size());
        int page_now=position+1;
        holder.page_now.setText(""+page_now);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView nomFreelancerHome, numberCoursHome, page_now, page_total;
        SimpleRatingBar numberRatingBarHome;
        LinearLayout altImage ;
        TextView altTextName;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.image_freelancer_home);
            nomFreelancerHome=itemView.findViewById(R.id.id_nom_freelancer_home);
            numberCoursHome=itemView.findViewById(R.id.id_nombre_cours_home);
            numberRatingBarHome=itemView.findViewById(R.id.id_simpe_rating_bar_home);
            page_now=itemView.findViewById(R.id.id_page_now_home);
            page_total=itemView.findViewById(R.id.page_total_home);
             altImage = (LinearLayout) itemView.findViewById(R.id.altImage);
            altTextName= (TextView) itemView.findViewById(R.id.altTextName);

        }
    }
}
