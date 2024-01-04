package com.ubayaprojectnmp.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Paragraf(var idpar:Int,
                    var cerbung_id:Int,
                    var users_id:Int,
                    var name:String,
                    var isi:String,
                    var waktu_dibuat:String):Parcelable


