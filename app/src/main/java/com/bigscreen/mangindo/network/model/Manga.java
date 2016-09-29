package com.bigscreen.mangindo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Manga implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("idServer")
    private int idServer;

    @SerializedName("favorite")
    private int favorite;

    @SerializedName("hiddenNewChapter")
    private String hiddenNewChapter;

    @SerializedName("genre")
    private String genre;

    @SerializedName("hidden_komik")
    private String hiddenKomik;

    @SerializedName("hitungWaktu")
    private String hitungWaktu;

    @SerializedName("icon_komik")
    private String iconKomik;

    @SerializedName("judul")
    private String judul;

    @SerializedName("lastModified")
    private String lastModified;

    @SerializedName("nama_lain")
    private String namaLain;

    @SerializedName("new_chapter")
    private String newChapter;

    @SerializedName("pengarang")
    private String pengarang;

    @SerializedName("published")
    private String published;

    @SerializedName("read")
    private String read;

    @SerializedName("status")
    private String status;

    @SerializedName("summary")
    private String summary;

    @SerializedName("waktu")
    private String waktu;

    public Manga() {
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getHiddenKomik() {
        return hiddenKomik;
    }

    public void setHiddenKomik(String hiddenKomik) {
        this.hiddenKomik = hiddenKomik;
    }

    public String getHiddenNewChapter() {
        return hiddenNewChapter;
    }

    public void setHiddenNewChapter(String hiddenNewChapter) {
        this.hiddenNewChapter = hiddenNewChapter;
    }

    public String getHitungWaktu() {
        return hitungWaktu;
    }

    public void setHitungWaktu(String hitungWaktu) {
        this.hitungWaktu = hitungWaktu;
    }

    public String getIconKomik() {
        return iconKomik;
    }

    public void setIconKomik(String iconKomik) {
        this.iconKomik = iconKomik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getNamaLain() {
        return namaLain;
    }

    public void setNamaLain(String namaLain) {
        this.namaLain = namaLain;
    }

    public String getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(String newChapter) {
        this.newChapter = newChapter;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
        dest.writeInt(this.idServer);
        dest.writeInt(this.favorite);
        dest.writeString(this.hiddenNewChapter);
        dest.writeString(this.genre);
        dest.writeString(this.hiddenKomik);
        dest.writeString(this.hitungWaktu);
        dest.writeString(this.iconKomik);
        dest.writeString(this.judul);
        dest.writeString(this.lastModified);
        dest.writeString(this.namaLain);
        dest.writeString(this.newChapter);
        dest.writeString(this.pengarang);
        dest.writeString(this.published);
        dest.writeString(this.read);
        dest.writeString(this.status);
        dest.writeString(this.summary);
        dest.writeString(this.waktu);
    }

    protected Manga(Parcel in) {
        this.id = in.readInt();
        this.idServer = in.readInt();
        this.favorite = in.readInt();
        this.hiddenNewChapter = in.readString();
        this.genre = in.readString();
        this.hiddenKomik = in.readString();
        this.hitungWaktu = in.readString();
        this.iconKomik = in.readString();
        this.judul = in.readString();
        this.lastModified = in.readString();
        this.namaLain = in.readString();
        this.newChapter = in.readString();
        this.pengarang = in.readString();
        this.published = in.readString();
        this.read = in.readString();
        this.status = in.readString();
        this.summary = in.readString();
        this.waktu = in.readString();
    }

    public static final Parcelable.Creator<Manga> CREATOR = new Parcelable.Creator<Manga>() {
        @Override
        public Manga createFromParcel(Parcel source) {
            return new Manga(source);
        }

        @Override
        public Manga[] newArray(int size) {
            return new Manga[size];
        }
    };

    @Override
    public String toString() {
        return "Manga{" +
                "favorite=" + favorite +
                ", id=" + id +
                ", idServer=" + idServer +
                ", hiddenNewChapter=" + hiddenNewChapter +
                ", genre='" + genre + '\'' +
                ", hiddenKomik='" + hiddenKomik + '\'' +
                ", hitungWaktu='" + hitungWaktu + '\'' +
                ", iconKomik='" + iconKomik + '\'' +
                ", judul='" + judul + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", namaLain='" + namaLain + '\'' +
                ", newChapter='" + newChapter + '\'' +
                ", pengarang='" + pengarang + '\'' +
                ", published='" + published + '\'' +
                ", read='" + read + '\'' +
                ", status='" + status + '\'' +
                ", summary='" + summary + '\'' +
                ", waktu='" + waktu + '\'' +
                '}';
    }
}
