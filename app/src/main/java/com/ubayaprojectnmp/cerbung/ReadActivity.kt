package com.ubayaprojectnmp.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.ActivityReadBinding

class ReadActivity : AppCompatActivity() {
    private lateinit var binding:ActivityReadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id = intent.getIntExtra("ID",-1)
        var url = Global.cerbung[id].imgurl
        var builder = Picasso.Builder(this)
        with(binding){
            builder.listener { picasso, url, exception -> exception.printStackTrace() }
            builder.build().load(url).into(imagePosterRead)
            textTitleRead.text = Global.cerbung[id].title
            textAuthorRead.text = Global.cerbung[id].creator
            var info = "Par: ${Global.cerbung[id].num_paragraf.toString()}; Like: ${Global.cerbung[id].like.toString()}"
            textInfo.setText(info)
            textParagraphs.text = Global.cerbung[id].paragraf
            textCreateRead.text = Global.cerbung[id].tglDibuat
        }
        binding.BtnBack.setOnClickListener{
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
    }
}