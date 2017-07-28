package com.bigscreen.mangindo.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class MangaImage(@SerializedName("id")
                 var id: Int = 0,

                 @SerializedName("page")
                 var page: Int = 0,

                 @SerializedName("url")
                 var url: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(page)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MangaImage> {
        override fun createFromParcel(parcel: Parcel): MangaImage {
            return MangaImage(parcel)
        }

        override fun newArray(size: Int): Array<MangaImage?> {
            return arrayOfNulls(size)
        }
    }
}