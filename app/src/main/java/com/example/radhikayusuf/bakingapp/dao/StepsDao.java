package com.example.radhikayusuf.bakingapp.dao;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author radhikayusuf.
 */

public class StepsDao implements Parcelable {

    private int id;
    private String shortDescription, description, videoURL, thumbnailURL;


    protected StepsDao(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StepsDao> CREATOR = new Creator<StepsDao>() {
        @Override
        public StepsDao createFromParcel(Parcel in) {
            return new StepsDao(in);
        }

        @Override
        public StepsDao[] newArray(int size) {
            return new StepsDao[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public static Creator<StepsDao> getCREATOR() {
        return CREATOR;
    }
}
