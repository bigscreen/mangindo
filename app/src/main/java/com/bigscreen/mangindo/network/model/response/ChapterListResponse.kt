package com.bigscreen.mangindo.network.model.response

import android.os.Parcel
import android.os.Parcelable
import com.bigscreen.mangindo.network.model.Chapter
import com.google.gson.annotations.SerializedName


data class ChapterListResponse(@SerializedName("komik") var comics: List<Chapter> = arrayListOf()) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Chapter))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(comics)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ChapterListResponse> {
        override fun createFromParcel(parcel: Parcel): ChapterListResponse {
            return ChapterListResponse(parcel)
        }

        override fun newArray(size: Int): Array<ChapterListResponse?> {
            return arrayOfNulls(size)
        }
    }
}