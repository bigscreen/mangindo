package com.bigscreen.mangindo.network.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Manga(@SerializedName("id")
                 var id: Int = 0,

                 @SerializedName("idServer")
                 var serverId: Int = 0,

                 @SerializedName("favorite")
                 var favorite: Int = 0,

                 @SerializedName("hiddenNewChapter")
                 var hiddenNewChapter: String = "",

                 @SerializedName("genre")
                 var genre: String = "",

                 @SerializedName("hidden_komik")
                 var hiddenComic: String = "",

                 @SerializedName("icon_komik")
                 var comicIcon: String = "",

                 @SerializedName("judul")
                 var title: String = "",

                 @SerializedName("lastModified")
                 var lastModified: String = "",

                 @SerializedName("nama_lain")
                 var alias: String = "",

                 @SerializedName("new_chapter")
                 var newChapter: String = "",

                 @SerializedName("pengarang")
                 var author: String = "",

                 @SerializedName("published")
                 var published: String = "",

                 @SerializedName("read")
                 var read: String = "",

                 @SerializedName("status")
                 var status: String = "",

                 @SerializedName("summary")
                 var summary: String = "",

                 @SerializedName("waktu")
                 var time: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(serverId)
        parcel.writeInt(favorite)
        parcel.writeString(hiddenNewChapter)
        parcel.writeString(genre)
        parcel.writeString(hiddenComic)
        parcel.writeString(comicIcon)
        parcel.writeString(title)
        parcel.writeString(lastModified)
        parcel.writeString(alias)
        parcel.writeString(newChapter)
        parcel.writeString(author)
        parcel.writeString(published)
        parcel.writeString(read)
        parcel.writeString(status)
        parcel.writeString(summary)
        parcel.writeString(time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Manga> {
        override fun createFromParcel(parcel: Parcel): Manga {
            return Manga(parcel)
        }

        override fun newArray(size: Int): Array<Manga?> {
            return arrayOfNulls(size)
        }
    }
}