package com.example.ekontest_hackathon;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractImageNameFromUrl {
    public ExtractImageNameFromUrl() {
    }
    public String getImageStorageLocation(String url){

        Pattern p = Pattern.compile("\\d{13}");
        Matcher m = p.matcher(url);
        if(m.matches()){
            url=m.group(1);
        }
        return url;
    }
}
