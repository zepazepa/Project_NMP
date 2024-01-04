package com.ubayaprojectnmp.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayaprojectnmp.cerbung.databinding.ActivitySignUpBinding
import org.json.JSONObject

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener {
            var username = binding.editTextRegistName.text.toString()
            var imgurl = binding.editTextRegistURL.text.toString()
            var pwd = binding.editTextRegistPass.text.toString()
            var con_pwd = binding.editTextRegistPass2.text.toString()
            if(pwd!=con_pwd){
                Toast.makeText(this,"Password dan Konfirmasi Password tidak sama!", Toast.LENGTH_SHORT).show()
            }
            else {
                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.me/native/160421033/user_register.php"
                var stringRequest = object : StringRequest(
                    Request.Method.POST, url,
                    {
                        Log.d("apiresult", it)
                        val obj = JSONObject(it)
                        Toast.makeText(
                            this,
                            "Silahkan masukkan password kembali",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, LogInActivity::class.java)
                        intent.putExtra("USERNAME", username)
                        startActivity(intent)
                        this.finish()
                    },

                    {
                        Log.e("apiresult", it.message.toString())
                        Toast.makeText(this, "Gagal register", Toast.LENGTH_SHORT).show()
                    }) {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["password"] = pwd
                        params["url_profile"] = imgurl
                        return params
                    }
                }
                q.add(stringRequest)
            }
        }
    }
}