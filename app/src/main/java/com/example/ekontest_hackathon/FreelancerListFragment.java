package com.example.ekontest_hackathon;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;


public class FreelancerListFragment extends Fragment {
    GridView mGridView;
    freelancerInterface listener;
    // Important when you have a listener with an interface
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if( context instanceof freelancerInterface){
            listener = (freelancerInterface) context;
        }else {
            throw new ClassCastException(context.toString()+ "must implement listener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_freelancer_list, container, false);

        final String[] number = {"Edouard Chevens", "Osson Sergio", "Edouard Nael","Romulus Ronick","Beaubrun Carl","Remy Clivenson", "Dorceus Amos", "Pompilus Armstrong", "Mizaine Valen's"};
        int [] img = {R.drawable.messi, R.drawable.cr7, R.drawable.kaka, R.drawable.luka,
                R.drawable.zidane, R.drawable.cr7, R.drawable.kaka, R.drawable.zidane, R.drawable.luka};
        mGridView = view.findViewById(R.id.freelancer_gridview);
        MainAdaper mainAdaper = new MainAdaper(getContext(), number, img);
        mGridView.setAdapter(mainAdaper);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"You clicked on "+ number[position], Toast.LENGTH_LONG).show();
                listener.onClickFreelancer(number[position]);
            }
        });
        return  view;
    }
    public interface freelancerInterface{
       public void onClickFreelancer(String name);
    }
}