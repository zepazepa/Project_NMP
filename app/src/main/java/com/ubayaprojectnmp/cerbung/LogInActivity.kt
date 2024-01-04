package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayaprojectnmp.cerbung.databinding.ActivityLogInBinding
import org.json.JSONObject

class LogInActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLogInBinding
    var regUser:String = ""
    var regPwd:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getStringExtra("USERNAME")!=null){
            regUser = intent.getStringExtra("USERNAME").toString()
            binding.editTextNameInp.setText(regUser)
        }

        binding.buttonLogIn.setOnClickListener {
            val sharedFile = "com.ubayaprojectnmp.cerbung"
            val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            var username = binding.editTextNameInp.text.toString()
            var pwd = binding.editTextPassInp.text.toString()

            if (username != "" || pwd != "") {
                val q = Volley.newRequestQueue(this)
                val url = "https://ubaya.me/native/160421033/user_ceklogin.php"
                var stringRequest = object: StringRequest(
                    Request.Method.POST, url,
                    {
                        Log.d("apiresult", it)
                        val obj = JSONObject(it)
                        if(obj.getString("result")=="OK"){
                            val data = obj.getJSONArray("data")
                            if (data.length() > 0) {
                                val dataUser = data.getJSONObject(0)
                                val sType = object : TypeToken<User>() {}.type
                                val user = Gson().fromJson(dataUser.toString(), sType) as User
                                Log.d("apiresult", user.toString())
                                editor.putInt("user_id", user.iduser)
                                editor.putString("user_name", user.name)
                                editor.putString("user_profile", user.url_photo)
                                editor.apply()
                                Toast.makeText(this,"Selamat datang, ${user.name}", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                startActivity(intent)
                                this.finish()
                            }
                        }
                        else {
                            Toast.makeText(this,"Username/Password salah", Toast.LENGTH_SHORT).show()
                        }
                    },

                    {
                        Log.e("apiresult", it.message.toString())
                        Toast.makeText(this,"Username/Password salah", Toast.LENGTH_SHORT).show()
                    })               {
                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["username"] = username
                        params["password"] = pwd
                        return params
                    }
                }

                q.add(stringRequest)
            }
            else{
                Toast.makeText(this,"Username/Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}