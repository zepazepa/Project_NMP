package com.ubayaprojectnmp.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(var iduser:Int, var name:String, var password:String, var url_photo:String, var waktu_dibuat:String): Parcelable