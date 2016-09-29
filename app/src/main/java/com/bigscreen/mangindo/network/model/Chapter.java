package com.bigscreen.mangindo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Chapter implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("judul")
    private String judul;

    @SerializedName("waktu")
    private String waktu;

    @SerializedName("hidden_komik")
    private String hiddenKomik;

    @SerializedName("hidden_chapter")
    private String hiddenChapter;

    public String getHiddenChapter() {
        return hiddenChapter;
    }

    public void setHiddenChapter(String hiddenChapter) {
        this.hiddenChapter = hiddenChapter;
    }

    public String getHiddenKomik() {
        return hiddenKomik;
    }

    public void setHiddenKomik(String hiddenKomik) {
        this.hiddenKomik = hiddenKomik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.judul);
        dest.writeString(this.waktu);
        dest.writeString(this.hiddenKomik);
        dest.writeString(this.hiddenChapter);
    }

    public Chapter() {
    }

    protected Chapter(Parcel in) {
        this.id = in.readInt();
        this.judul = in.readString();
        this.waktu = in.readString();
        this.hiddenKomik = in.readString();
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
                ", judul='" + judul + '\'' +
                ", waktu='" + waktu + '\'' +
                ", hiddenKomik='" + hiddenKomik + '\'' +
                '}';
    }

}
