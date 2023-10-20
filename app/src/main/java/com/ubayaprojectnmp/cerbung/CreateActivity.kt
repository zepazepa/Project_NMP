package com.ubayaprojectnmp.cerbung

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.ubayaprojectnmp.cerbung.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    var paragraf = "";
    var access="";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val adapter = ArrayAdapter(this, R.layout.simple_list_item_1,Global.genre)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenreCreate.adapter = adapter


        if (intent.getStringExtra("TITLE")!=null){
            binding.editTextTitleCreate.setText(intent.getStringExtra("TITLE").toString())
            binding.editTextSynopsisCreate.setText(intent.getStringExtra("SYNOPSIS").toString())
            binding.editTextURLCreate.setText(intent.getStringExtra("URL").toString())
            binding.spinnerGenreCreate.setSelection(intent.getStringExtra("GENRE").toString().toInt())
        }
        if (intent.getStringExtra("PARAGRAF")!=null){
            paragraf = intent.getStringExtra("PARAGRAF").toString();
            access = intent.getStringExtra("ACCESS").toString();
        }
        binding.buttonNextCreate.setOnClickListener {
            var title = binding.editTextTitleCreate.text.toString()
            var sinopsis = binding.editTextSynopsisCreate.text.toString()
            var imgurl = binding.editTextURLCreate.text.toString()
            var genre = binding.spinnerGenreCreate.selectedItemId.toString()
            if (title=="" ||sinopsis==""||imgurl==""||genre==""){
                Toast.makeText(this,"Isi semua data terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, Create2Activity::class.java)
                intent.putExtra("TITLE",title)
                intent.putExtra("SYNOPSIS",sinopsis)
                intent.putExtra("URL",imgurl)
                intent.putExtra("GENRE",genre)
                intent.putExtra("ACCESS",access)
                intent.putExtra("PARAGRAF",paragraf)
                startActivity(intent)
            }
        }
    }
}