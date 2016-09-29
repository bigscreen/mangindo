package com.bigscreen.mangindo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MangaImage implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("page")
    private int page;

    @SerializedName("url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.page);
        dest.writeString(this.url);
    }

    public MangaImage() {
    }

    protected MangaImage(Parcel in) {
        this.id = in.readInt();
        this.page = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MangaImage> CREATOR = new Parcelable.Creator<MangaImage>() {
        @Override
        public MangaImage createFromParcel(Parcel source) {
            return new MangaImage(source);
        }

        @Override
        public MangaImage[] newArray(int size) {
            return new MangaImage[size];
        }
    };

    @Override
    public String toString() {
        return "MangaImage{" +
                "id=" + id +
                ", page=" + page +
                ", url='" + url + '\'' +
                '}';
    }
}
