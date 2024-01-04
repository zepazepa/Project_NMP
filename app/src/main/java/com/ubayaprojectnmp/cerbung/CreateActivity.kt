package com.ubayaprojectnmp.cerbung

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayaprojectnmp.cerbung.databinding.ActivityCreateBinding
import org.json.JSONObject

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    var paragraf = ""
    var access=""
    var listGenre:ArrayList<Genre> = arrayListOf()
    fun getGenre(){
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421033/genre_getall.php"
        var stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result")=="OK"){
                    val dataUser = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Genre>>() { }.type
                    listGenre = Gson().fromJson(dataUser.toString(), sType) as  ArrayList<Genre>
                    var adapter = ArrayAdapter(this, R.layout.simple_list_item_1, listGenre)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerGenreCreate.adapter = adapter
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                return super.getParams()
            }
        }
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getGenre()
        if (intent.getStringExtra("TITLE")!=null){
            binding.editTextTitleCreate.setText(intent.getStringExtra("TITLE").toString())
            binding.editTextSynopsisCreate.setText(intent.getStringExtra("SYNOPSIS").toString())
            binding.editTextURLCreate.setText(intent.getStringExtra("URL").toString())
        }
        if (intent.getStringExtra("PARAGRAF")!=null){
            paragraf = intent.getStringExtra("PARAGRAF").toString();
            access = intent.getStringExtra("ACCESS").toString();
        }
        binding.buttonNextCreate.setOnClickListener {
            var title = binding.editTextTitleCreate.text.toString()
            var sinopsis = binding.editTextSynopsisCreate.text.toString()
            var imgurl = binding.editTextURLCreate.text.toString()
            var genre = binding.spinnerGenreCreate.selectedItem.toString()
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