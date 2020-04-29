package com.example.news.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Submode (
    val name: String,
    val type:ModeType,
    val queryUrl:String
)

@Parcelize
enum class ModeType : Parcelable {
    CATEGORY, SOURCE, COUNTRY
}