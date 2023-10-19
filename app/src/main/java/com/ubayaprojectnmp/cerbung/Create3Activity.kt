package com.ubayaprojectnmp.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ubayaprojectnmp.cerbung.databinding.ActivityCreate3Binding

class Create3Activity : AppCompatActivity() {
    private lateinit var binding:ActivityCreate3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreate3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var title = intent.getStringExtra("TITLE").toString()
        var sinopsis = intent.getStringExtra("SYNOPSIS").toString()
        var imgurl = intent.getStringExtra("URL").toString()
        var genre = intent.getStringExtra("GENRE").toString()
        var access = intent.getStringExtra("ACCESS").toString()
        var paragraf = intent.getStringExtra("PARAGRAF").toString()
        with(binding){
            textTitleConfirm.setText(title)
            textViewSynopsisConfirm.setText(sinopsis)
            textViewGenre.setText(Global.genre[genre.toInt()].toString())
            textViewAccess.setText(access)
            textViewParagraphConfirm.setText(paragraf)
        }
        binding.buttonPrevCreate.setOnClickListener {
            val intent = Intent(this,Create2Activity::class.java)
            /*
            intent.putExtra("TITLE",intent.getStringExtra("TITLE").toString())
            intent.putExtra("SYNOPSIS",intent.getStringExtra("SYNOPSIS").toString())
            intent.putExtra("URL",intent.getStringExtra("URL").toString())
            intent.putExtra("GENRE",intent.getStringExtra("GENRE").toString())
            intent.putExtra("ACCESS",intent.getStringExtra("ACCESS").toString())
            intent.putExtra("PARAGRAF",intent.getStringExtra("PARAGRAF").toString())*/
            startActivity(intent)
        }
        binding.buttonPublishCreate.setOnClickListener {
            if(binding.checkBox.isChecked){
                Global.cerbung.add(Cerbung(Global.cerbung.size, title,"aku",
                    Genre(genre.toInt()+1,binding.textViewGenre.text.toString()),
                    "31/10/2023",0,imgurl,sinopsis,1,paragraf))
                Toast.makeText(this,"Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                val intent= Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Check Terms and Conditions First", Toast.LENGTH_SHORT).show()
            }
        }
    }
}