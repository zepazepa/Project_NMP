package com.ubayaprojectnmp.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ubayaprojectnmp.cerbung.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLogInBinding
    var regUser:String = ""
    var regPwd:String = ""
    fun checkAkun(username:String, pwd:String):Boolean{
        for (akun in Global.akun) if(username==akun.username&&pwd==akun.password) return true
        return false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getStringExtra("USERNAME")!=null){
            regUser = intent.getStringExtra("USERNAME").toString()
            binding.editTextNameInp.setText(regUser)
        }

        binding.buttonLogIn.setOnClickListener {
            var username = binding.editTextNameInp.text.toString()
            var pwd = binding.editTextPassInp.text.toString()

            if (username != "" || pwd != ""){
                if (checkAkun(username,pwd)){
                    Toast.makeText(this,"Login Berhasil!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("user",username)
                    startActivity(intent)
                    this.finish()
                }
                else{
                    Toast.makeText(this,"Username/Password salah", Toast.LENGTH_SHORT).show()
                }
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