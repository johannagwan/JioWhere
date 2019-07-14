package com.example.jiowhere;

public class UploadImage {
    private String mName;
    private String mImageUrl;


    public  UploadImage() {
        //empty constructed needed (????)
    }

    public UploadImage(String mName, String mImageUrl) {
        if(mName.trim().equals("")) {
            mName = "No Name";
        }

        this.mImageUrl = mImageUrl;
        this.mName = mName;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmName() {
        return mName;
    }
}
