package com.ubayaprojectnmp.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(val id:Int, val nama:String) :Parcelable{
    override fun toString(): String {
        return nama
    }
}