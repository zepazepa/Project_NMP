package com.ubayaprojectnmp.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.ubayaprojectnmp.cerbung.databinding.ActivityCreate2Binding

class Create2Activity : AppCompatActivity() {
    private lateinit var binding:ActivityCreate2Binding
    var access =""
    var paragraf =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreate2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var title = intent.getStringExtra("TITLE").toString()
        var sinopsis = intent.getStringExtra("SYNOPSIS").toString()
        var imgurl = intent.getStringExtra("URL").toString()
        var genre = intent.getStringExtra("GENRE").toString()

        binding.GroupAccess.setOnCheckedChangeListener { radioGroup, id ->
            /* val selectedRadio = findViewById<RadioButton>(id)
            selectedRadio.setTextColor(Color.RED)*/
            val radio = findViewById<RadioButton>(binding.GroupAccess.checkedRadioButtonId)
            access = radio.tag.toString()
        }
        binding.editTextParagraphCreate.doOnTextChanged{s,start,before,count->
            binding.textViewWordCount.text = "(${binding.editTextParagraphCreate.text.toString().length} out of 70 characters)"
        }
        if (intent.getStringExtra("PARAGRAF")!=null){
            var acc = intent.getStringExtra("ACCESS")
            if (acc == "RESTRICTED"){
                binding.radioButtonAccess1.isChecked = true
            }
            else if(acc =="PUBLIC"){
                binding.radioButtonAccess2.isChecked = true
            }
            binding.editTextParagraphCreate.setText(intent.getStringExtra("PARAGRAF").toString())
        }
        binding.buttonPrevCreate2.setOnClickListener {
            val intent = Intent(this,CreateActivity::class.java)
            paragraf = binding.editTextParagraphCreate.text.toString()
            intent.putExtra("TITLE",title)
            intent.putExtra("SYNOPSIS",sinopsis)
            intent.putExtra("URL",imgurl)
            intent.putExtra("GENRE",genre)
            intent.putExtra("ACCESS", access)
            intent.putExtra("PARAGRAF", paragraf)
            startActivity(intent)
        }
        binding.buttonNextCreate2.setOnClickListener {
            paragraf = binding.editTextParagraphCreate.text.toString()
            if (access=="" ||paragraf==""){
                Toast.makeText(this,"Isi semua data terlebih dahulu!", Toast.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this, Create3Activity::class.java)
                intent.putExtra("TITLE", title)
                intent.putExtra("SYNOPSIS", sinopsis)
                intent.putExtra("URL", imgurl)
                intent.putExtra("GENRE", genre)
                intent.putExtra("ACCESS", access)
                intent.putExtra("PARAGRAF", paragraf)
                startActivity(intent)
            }
        }
    }
}