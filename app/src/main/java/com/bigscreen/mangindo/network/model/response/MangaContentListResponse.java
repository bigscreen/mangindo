package com.bigscreen.mangindo.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigscreen.mangindo.network.model.MangaImage;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MangaContentListResponse implements Parcelable {

    @SerializedName("chapter")
    private List<MangaImage> chapter;

    public List<MangaImage> getChapter() {
        return chapter;
    }

    public void setChapter(List<MangaImage> chapter) {
        this.chapter = chapter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.chapter);
    }

    public MangaContentListResponse() {
    }

    protected MangaContentListResponse(Parcel in) {
        this.chapter = in.createTypedArrayList(MangaImage.CREATOR);
    }

    public static final Parcelable.Creator<MangaContentListResponse> CREATOR = new Parcelable.Creator<MangaContentListResponse>() {
        @Override
        public MangaContentListResponse createFromParcel(Parcel source) {
            return new MangaContentListResponse(source);
        }

        @Override
        public MangaContentListResponse[] newArray(int size) {
            return new MangaContentListResponse[size];
        }
    };

    @Override
    public String toString() {
        return "MangaContentListResponse{" +
                "chapter=" + chapter +
                '}';
    }
}
