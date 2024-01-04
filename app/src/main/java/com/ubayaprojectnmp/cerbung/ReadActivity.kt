package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.ActivityReadBinding
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReadActivity : AppCompatActivity() {
    private lateinit var binding:ActivityReadBinding
    var listParagraf: ArrayList<Paragraf> = arrayListOf()

    fun loadParagraf(){
        val q2 = Volley.newRequestQueue(this)
        val url2 = "https://ubaya.me/native/160421033/paragraf_read.php"
        var stringRequest2 = object: StringRequest(
            Request.Method.POST, url2,
            {
                Log.d("apiresult", it)
                val obj2 = JSONObject(it)
                if(obj2.getString("result")=="OK"){
                    val dataPar = obj2.getJSONArray("data")
                    Log.d("apiresult", dataPar.toString())
                    val sType2 = object : TypeToken<List<Paragraf>>() { }.type
                    listParagraf = Gson().fromJson(dataPar.toString(), sType2) as ArrayList<Paragraf>
                    Log.d("apiresult", listParagraf.toString())
                    val lm = LinearLayoutManager(this)
                    with(binding.recyclerView) {
                        layoutManager = lm
                        adapter = ParagrafAdapter(listParagraf)
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["cerbung_id"] = intent.getIntExtra("cerbung_id",0).toString()
                return params
            }
        }
        q2.add(stringRequest2)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var id = intent.getIntExtra("cerbung_id",0)
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421033/cerbung_read.php"
        var stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    if (data.length() > 0) {
                        val dataCerbung = data.getJSONObject(0)
                        val sType = object : TypeToken<Cerbung>() {}.type
                        val cerbung = Gson().fromJson(dataCerbung.toString(), sType) as Cerbung

                        with(binding) {
                            var img_url = cerbung.img_url
                            var builder = Picasso.Builder(root.context)
                            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
                            builder.build().load(img_url).into(imagePosterRead)
                            textTitleRead.setText(cerbung.title)
                            textAuthorRead.setText(cerbung.name)
                            var waktu_dibuat = cerbung.waktu_dibuat
                            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                            val waktu = LocalDateTime.parse(waktu_dibuat, formatter)
                            textCreateRead.setText(waktu.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString())
                            textLike.setText(cerbung.like.toString())
                            textPara.setText(cerbung.num_paragraf.toString())

                            if (cerbung.akses == "RESTRICTED"){
                                binding.editTextParagraphCreate.visibility = View.GONE
                                binding.textViewWordCount.visibility = View.GONE
                                binding.btnSubmit.setText("Request")
                                binding.btnSubmit.tag = "RESTRICTED"
                            }

                        }
                        loadParagraf()
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["cerbung_id"] = id.toString()
                return params
            }
        }
        q.add(stringRequest)
        binding.imgLike.setOnClickListener{
            if (binding.imgLike.tag == "LIKE") {
                binding.imgLike.setImageResource(R.drawable.icon_notlike)
                binding.imgLike.tag = "NOTLIKE"
                binding.textLike.text = (binding.textLike.text.toString().toInt() - 1).toString()

            } else if (binding.imgLike.tag == "NOTLIKE") {
                binding.imgLike.setImageResource(R.drawable.icon_like)
                binding.imgLike.tag = "LIKE"
                binding.textLike.text = (binding.textLike.text.toString().toInt() + 1).toString()
            }
        }
        binding.editTextParagraphCreate.doOnTextChanged{s,start,before,count->
            binding.textViewWordCount.text = "(${binding.editTextParagraphCreate.text.toString().length} out of 70 characters)"
        }
        binding.btnSubmit.setOnClickListener{
            if (binding.btnSubmit.tag == "PUBLIC"){
                val qAdd = Volley.newRequestQueue(this)
                val urlAdd = "https://ubaya.me/native/160421033/paragraf_submit.php"
                var stringRequestAdd = object: StringRequest(
                    Request.Method.POST, urlAdd,
                    {
                        Log.d("apiresult", it)
                        val objAdd = JSONObject(it)
                        if(objAdd.getString("result")=="OK"){
                            Toast.makeText(this,"Paragraf berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                        }
                    },
                    {
                        Log.e("apiresult", it.message.toString())
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["cerbung_id"] = id.toString()
                        val sharedFile = "com.ubayaprojectnmp.cerbung"
                        val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
                        val users_id = sharedPreferences.getInt("user_id", 0)
                        params["users_id"] = users_id.toString()
                        params["isi"] = binding.editTextParagraphCreate.text.toString()
                        return params
                    }
                }
                qAdd.add(stringRequestAdd)
                loadParagraf()
            }
            else{
                Toast.makeText(this,"Request berhasil dikirimkan!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnFollow.setOnClickListener{
            val qFol = Volley.newRequestQueue(this)
            val urlFol = "https://ubaya.me/native/160421033/user_follow.php"
            var stringRequestFol = object: StringRequest(
                Request.Method.POST, urlFol,
                {
                    Log.d("apiresult", it)
                    val objAdd = JSONObject(it)
                    if(objAdd.getString("result")=="OK"){
                        Toast.makeText(this,"Cerbung Berhasil difollow", Toast.LENGTH_SHORT).show()
                    }
                },
                {
                    Log.e("apiresult", it.message.toString())
                }) {
                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["cerbung_id"] = id.toString()
                    val sharedFile = "com.ubayaprojectnmp.cerbung"
                    val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
                    val users_id = sharedPreferences.getInt("user_id", 0)
                    params["users_id"] = users_id.toString()
                    return params
                }
            }
            qFol.add(stringRequestFol)
        }
    }
}