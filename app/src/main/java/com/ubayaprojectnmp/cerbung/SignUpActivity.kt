package com.ubayaprojectnmp.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ubayaprojectnmp.cerbung.databinding.ActivitySignUpBinding

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
            else{
                val intent = Intent(this,LogInActivity::class.java)
                Global.akun.add(Akun(username,pwd))
                intent.putExtra("USERNAME",username)
                startActivity(intent)
                this.finish()
            }
        }
    }
}