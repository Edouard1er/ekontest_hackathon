package com.example.ekontest_hackathon;


import android.text.TextUtils;
import android.widget.EditText;

import java.util.ArrayList;

public class EmptyField {
    private ArrayList<EditText> collectionEditText;

    public EmptyField(ArrayList<EditText> collectionEditText) {
        this.collectionEditText = collectionEditText;
    }

    public boolean isAllFieldFilled() {
        for(int i = 0; i < this.collectionEditText.size(); i++){
            if( TextUtils.isEmpty(this.collectionEditText.get(i).getText())){
                return false;
            }
        }
        return true;
    }

}

