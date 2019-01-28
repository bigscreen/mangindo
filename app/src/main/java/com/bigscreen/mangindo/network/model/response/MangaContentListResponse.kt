package com.bigscreen.mangindo.network.model.response

import android.os.Parcel
import android.os.Parcelable
import com.bigscreen.mangindo.common.extension.isNotAdsUrl
import com.bigscreen.mangindo.network.model.MangaImage
import com.google.gson.annotations.SerializedName


data class MangaContentListResponse(@SerializedName("chapter") var mangaImages: List<MangaImage>? = arrayListOf()) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(MangaImage))

    fun getNonAdsMangaContent(): MangaContentListResponse {
        return this.copy(mangaImages = mangaImages?.filter { it.url.isNotAdsUrl() })
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(mangaImages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MangaContentListResponse> {
        override fun createFromParcel(parcel: Parcel): MangaContentListResponse {
            return MangaContentListResponse(parcel)
        }

        override fun newArray(size: Int): Array<MangaContentListResponse?> {
            return arrayOfNulls(size)
        }
    }
}