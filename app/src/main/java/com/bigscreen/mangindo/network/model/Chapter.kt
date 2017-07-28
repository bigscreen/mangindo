package com.bigscreen.mangindo.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Chapter(
        @SerializedName("id")
        var id: Int = 0,

        @SerializedName("judul")
        var title: String = "",

        @SerializedName("waktu")
        var time: String = "",

        @SerializedName("hidden_komik")
        var hiddenComic: String = "",

        @SerializedName("hidden_chapter")
        var hiddenChapter: String = ""

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(time)
        parcel.writeString(hiddenComic)
        parcel.writeString(hiddenChapter)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Chapter> {
        override fun createFromParcel(parcel: Parcel): Chapter {
            return Chapter(parcel)
        }

        override fun newArray(size: Int): Array<Chapter?> {
            return arrayOfNulls(size)
        }
    }
}