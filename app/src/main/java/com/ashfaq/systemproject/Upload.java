package com.ashfaq.systemproject;

public class Upload {
    private String mName;
    private String mImgUrl;

    public Upload(){

    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmImgUrl() {
        return mImgUrl;
    }

    public void setmImgUrl(String mImgUrl) {
        this.mImgUrl = mImgUrl;
    }

    public Upload(String name, String imageUrl){
        if (name.trim().equals("")) {
            name="NoName";
        }
        mName = name;
        mImgUrl = imageUrl;
    }
}
