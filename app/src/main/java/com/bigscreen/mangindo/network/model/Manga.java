package com.bigscreen.mangindo.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Manga implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("idServer")
    private int serverId;

    @SerializedName("favorite")
    private int favorite;

    @SerializedName("hiddenNewChapter")
    private String hiddenNewChapter;

    @SerializedName("genre")
    private String genre;

    @SerializedName("hidden_komik")
    private String hiddenComic;

    @SerializedName("icon_komik")
    private String comicIcon;

    @SerializedName("judul")
    private String title;

    @SerializedName("lastModified")
    private String lastModified;

    @SerializedName("nama_lain")
    private String alias;

    @SerializedName("new_chapter")
    private String newChapter;

    @SerializedName("pengarang")
    private String author;

    @SerializedName("published")
    private String published;

    @SerializedName("read")
    private String read;

    @SerializedName("status")
    private String status;

    @SerializedName("summary")
    private String summary;

    @SerializedName("waktu")
    private String time;

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

    public String getHiddenComic() {
        return hiddenComic;
    }

    public void setHiddenComic(String hiddenComic) {
        this.hiddenComic = hiddenComic;
    }

    public String getHiddenNewChapter() {
        return hiddenNewChapter;
    }

    public void setHiddenNewChapter(String hiddenNewChapter) {
        this.hiddenNewChapter = hiddenNewChapter;
    }

    public String getComicIcon() {
        return comicIcon;
    }

    public void setComicIcon(String comicIcon) {
        this.comicIcon = comicIcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(String newChapter) {
        this.newChapter = newChapter;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.serverId);
        dest.writeInt(this.favorite);
        dest.writeString(this.hiddenNewChapter);
        dest.writeString(this.genre);
        dest.writeString(this.hiddenComic);
        dest.writeString(this.comicIcon);
        dest.writeString(this.title);
        dest.writeString(this.lastModified);
        dest.writeString(this.alias);
        dest.writeString(this.newChapter);
        dest.writeString(this.author);
        dest.writeString(this.published);
        dest.writeString(this.read);
        dest.writeString(this.status);
        dest.writeString(this.summary);
        dest.writeString(this.time);
    }

    protected Manga(Parcel in) {
        this.id = in.readInt();
        this.serverId = in.readInt();
        this.favorite = in.readInt();
        this.hiddenNewChapter = in.readString();
        this.genre = in.readString();
        this.hiddenComic = in.readString();
        this.comicIcon = in.readString();
        this.title = in.readString();
        this.lastModified = in.readString();
        this.alias = in.readString();
        this.newChapter = in.readString();
        this.author = in.readString();
        this.published = in.readString();
        this.read = in.readString();
        this.status = in.readString();
        this.summary = in.readString();
        this.time = in.readString();
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
                ", serverId=" + serverId +
                ", hiddenNewChapter=" + hiddenNewChapter +
                ", genre='" + genre + '\'' +
                ", hiddenComic='" + hiddenComic + '\'' +
                ", comicIcon='" + comicIcon + '\'' +
                ", title='" + title + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", alias='" + alias + '\'' +
                ", newChapter='" + newChapter + '\'' +
                ", author='" + author + '\'' +
                ", published='" + published + '\'' +
                ", read='" + read + '\'' +
                ", status='" + status + '\'' +
                ", summary='" + summary + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
