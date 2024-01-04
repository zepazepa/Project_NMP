package com.ubayaprojectnmp.cerbung

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cerbung (val idcerbung:Int,
                    val title:String,
                    val name:String,
                    val genre:Genre,
                    val like:Int,
                    val img_url:String,
                    val description:String,
                    val num_paragraf:Int,
                    val akses:String,
                    val waktu_dibuat:String):Parcelable{

}