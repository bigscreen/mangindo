package com.bigscreen.mangindo.network.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.bigscreen.mangindo.network.model.Manga;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewReleaseResponse implements Parcelable {

    @SerializedName("komik")
    private List<Manga> comics;

    public List<Manga> getComics() {
        return comics;
    }

    public void setMangas(List<Manga> komik) {
        this.comics = komik;
    }

    public NewReleaseResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.comics);
    }

    protected NewReleaseResponse(Parcel in) {
        this.comics = in.createTypedArrayList(Manga.CREATOR);
    }

    public static final Parcelable.Creator<NewReleaseResponse> CREATOR = new Parcelable.Creator<NewReleaseResponse>() {
        @Override
        public NewReleaseResponse createFromParcel(Parcel source) {
            return new NewReleaseResponse(source);
        }

        @Override
        public NewReleaseResponse[] newArray(int size) {
            return new NewReleaseResponse[size];
        }
    };

    @Override
    public String toString() {
        return "NewReleaseResponse{" +
                "comics=" + comics +
                '}';
    }
}
