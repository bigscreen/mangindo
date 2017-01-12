package com.bigscreen.mangindo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Chapter implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("judul")
    private String title;

    @SerializedName("time")
    private String time;

    @SerializedName("hidden_komik")
    private String hiddenComic;

    @SerializedName("hidden_chapter")
    private String hiddenChapter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHiddenComic() {
        return hiddenComic;
    }

    public void setHiddenComic(String hiddenComic) {
        this.hiddenComic = hiddenComic;
    }

    public String getHiddenChapter() {
        return hiddenChapter;
    }

    public void setHiddenChapter(String hiddenChapter) {
        this.hiddenChapter = hiddenChapter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.hiddenComic);
        dest.writeString(this.hiddenChapter);
    }

    public Chapter() {
    }

    protected Chapter(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.time = in.readString();
        this.hiddenComic = in.readString();
        this.hiddenChapter = in.readString();
    }

    public static final Parcelable.Creator<Chapter> CREATOR = new Parcelable.Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @Override
    public String toString() {
        return "Chapter{" +
                "hiddenChapter='" + hiddenChapter + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", hiddenComic='" + hiddenComic + '\'' +
                '}';
    }

}
