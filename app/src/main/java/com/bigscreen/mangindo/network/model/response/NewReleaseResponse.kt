package com.bigscreen.mangindo.network.model.response

import android.os.Parcel
import android.os.Parcelable
import com.bigscreen.mangindo.network.model.Manga
import com.google.gson.annotations.SerializedName


data class NewReleaseResponse(@SerializedName("komik")
                              var comics: List<Manga> = arrayListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Manga))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(comics)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewReleaseResponse> {
        override fun createFromParcel(parcel: Parcel): NewReleaseResponse {
            return NewReleaseResponse(parcel)
        }

        override fun newArray(size: Int): Array<NewReleaseResponse?> {
            return arrayOfNulls(size)
        }
    }
}
