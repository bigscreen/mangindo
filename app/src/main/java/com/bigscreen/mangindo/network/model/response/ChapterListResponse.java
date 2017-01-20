package com.bigscreen.mangindo.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigscreen.mangindo.network.model.Chapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterListResponse implements Parcelable {

    @SerializedName("komik")
    private List<Chapter> comics;

    public List<Chapter> getComics() {
        return comics;
    }

    public void setComics(List<Chapter> comics) {
        this.comics = comics;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.comics);
    }

    public ChapterListResponse() {
    }

    protected ChapterListResponse(Parcel in) {
        this.comics = in.createTypedArrayList(Chapter.CREATOR);
    }

    public static final Parcelable.Creator<ChapterListResponse> CREATOR = new Parcelable.Creator<ChapterListResponse>() {
        @Override
        public ChapterListResponse createFromParcel(Parcel source) {
            return new ChapterListResponse(source);
        }

        @Override
        public ChapterListResponse[] newArray(int size) {
            return new ChapterListResponse[size];
        }
    };

    @Override
    public String toString() {
        return "ChapterListResponse{" +
                "comics=" + comics +
                '}';
    }
}
