package com.example.ekontest_hackathon;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FabDocumentFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{
    FloatingActionButton mFloatingActionButton;
    String user="teacher";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_fab_document, container, false);
        mFloatingActionButton=view.findViewById(R.id.fab_document);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user=="student"){
                    Toast.makeText(getContext(), "Buy a document", Toast.LENGTH_LONG).show();
                }
                else{
                    setFab_available_document(v);
                }
            }
        });
        return view;
    }
    public void setFab_available_document(View view){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.setOnMenuItemClickListener(this);
        if(user=="freelancer"){
            popupMenu.inflate(R.menu.menu_fab_document_freelancer);
        }
        else if (user=="teacher"){
            popupMenu.inflate(R.menu.menu_fab_document);
        }
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.upload_free_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), UploadNormalDocument.class);
                startActivity(intent);
                return true;
            }
            case R.id.upload_payed_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), UploadPaidDocument.class);
                startActivity(intent);
                return true;
            }
            case R.id.buy_document: {
                Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                return true;
            }
            default: return false;
        }
    }
}