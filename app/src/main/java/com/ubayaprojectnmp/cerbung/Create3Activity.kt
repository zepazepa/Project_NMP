package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.ubayaprojectnmp.cerbung.databinding.ActivityCreate3Binding
import org.json.JSONObject

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
            textViewGenre.setText(genre.toString())
            textViewAccess.setText(access)
            textViewParagraphConfirm.setText(paragraf)
        }
        binding.buttonPrevCreate.setOnClickListener {
            val intent = Intent(this,Create2Activity::class.java)
            intent.putExtra("TITLE",title)
            intent.putExtra("SYNOPSIS",sinopsis)
            intent.putExtra("URL",imgurl)
            intent.putExtra("GENRE",genre)
            intent.putExtra("ACCESS",access)
            intent.putExtra("PARAGRAF",paragraf)
            startActivity(intent)
        }
        binding.buttonPublishCreate.setOnClickListener {
            if(binding.checkBox.isChecked){
                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.me/native/160421033/cerbung_add.php"
                val stringRequest = object : StringRequest(
                    Request.Method.POST, url,{
                        Log.d("apiresult", it)
                        val obj = JSONObject(it)
                        if (obj.getString("result")=="OK"){
                            val intent= Intent(this,HomeActivity::class.java)
                            startActivity(intent)
                            this.finish()
                        }
                        else{
                            Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show()
                        }
                    },{
                        Log.e("apiresult", it.message.toString())
                    }){
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["title"] = title
                        params["description"] = sinopsis
                        params["num_like"] = "0"
                        params["akses"] = access
                        params["genre"] = genre
                        params["img_url"] = imgurl
                        params["paragraf"] = paragraf
                        val sharedFile = "com.ubayaprojectnmp.cerbung"
                        val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
                        val id = sharedPreferences.getInt("user_id", 0)
                        params["users_id"] = id.toString()
                        return params
                    }
                }
                q.add(stringRequest)
            }
            else{
                Toast.makeText(this,"Check Terms and Conditions First", Toast.LENGTH_SHORT).show()
            }
        }
    }
}