package com.example.ekontest_hackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.List;

class SwipeUserAdapter extends RecyclerView.Adapter<SwipeUserAdapter.UserViewHolder> {
    Context mContext;
    List <SwipeUser> mUserList;

    public SwipeUserAdapter(Context context, List<SwipeUser> userList) {
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
        SwipeUser user= mUserList.get(position);
        holder.nomFreelancerHome.setText(user.getNomFreelancer());
        holder.numberRatingBarHome.setRating(user.getRatingBarNumber());
        holder.numberCoursHome.setText(user.getNombreCours());
        holder.icon.setImageResource(user.getIcon());
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
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.image_freelancer_home);
            nomFreelancerHome=itemView.findViewById(R.id.id_nom_freelancer_home);
            numberCoursHome=itemView.findViewById(R.id.id_nombre_cours_home);
            numberRatingBarHome=itemView.findViewById(R.id.id_simpe_rating_bar_home);
            page_now=itemView.findViewById(R.id.id_page_now_home);
            page_total=itemView.findViewById(R.id.page_total_home);

        }
    }
}
