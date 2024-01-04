package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.FragmentHomeBinding
import com.ubayaprojectnmp.cerbung.databinding.FragmentPrefsBinding
import org.json.JSONObject


class PrefsFragment : Fragment() {
    private lateinit var binding: FragmentPrefsBinding
    private var user_id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            user_id = it.getInt("user_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPrefsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedFile = "com.ubayaprojectnmp.cerbung"
        val sharedPreferences = activity?.getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        val id = sharedPreferences?.getInt("user_id", 0)
        val nightMode = sharedPreferences?.getBoolean("nightmode", false)
        val notif = sharedPreferences?.getBoolean("notif", false)
        val name = sharedPreferences?.getString("user_name", "")
        val img_url = sharedPreferences?.getString("user_profile", "")

        var builder = Picasso.Builder(view.context)
        builder.listener { picasso, url, exception -> exception.printStackTrace() }
        builder.build().load(img_url).into(binding.imageProfilePic)

        binding.editTextNameChange.setText(name)

        if (nightMode == true){
            binding.switchDark.isChecked=true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        binding.switchDark.setOnCheckedChangeListener { button, b ->
            if (b){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor?.putBoolean("nightmode", true)
                editor?.apply()
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor?.putBoolean("nightmode", false)
                editor?.apply()
            }
        }

        if (notif == true){
            binding.switchNotif.isChecked=true
            //make notif
        }
        binding.switchNotif.setOnCheckedChangeListener { button, b ->
            if (b){
                editor?.putBoolean("notif", true)
                editor?.apply()
            }
            else{
                editor?.putBoolean("notif", false)
                editor?.apply()
            }
        }
        binding.buttonSignOut.setOnClickListener(){
            val intent  = Intent(activity, LogInActivity::class.java)
            editor?.remove("user_id")
            editor?.remove("user_name")
            editor?.remove("user_profile")
            editor?.apply()
            startActivity(intent)
            activity?.finish()
        }
        binding.buttonChange.setOnClickListener(){
            var oldPass = binding.editTextPassOld.text.toString()
            var newPass = binding.editTextPassNew.text.toString()
            var confirmNewPass = binding.editTextPassConfirm.text.toString()
            if(oldPass!="" && newPass!="" && confirmNewPass!=""){
                if(oldPass == newPass){
                    Toast.makeText(view.context,"Password baru tidak boleh sama dengan password lama", Toast.LENGTH_SHORT).show()
                }
                else if(newPass != confirmNewPass){
                    Toast.makeText(view.context,"Password baru tidak sama dengan konfirmasi password", Toast.LENGTH_SHORT).show()
                }
                else if (newPass == confirmNewPass && oldPass!=newPass){
                    val q = Volley.newRequestQueue(view.context)
                    val url = "https://ubaya.me/native/160421033/user_update.php"
                    var stringRequest = object : StringRequest(
                        Request.Method.POST, url,
                        {
                            Log.d("apiresult", it)
                            val obj = JSONObject(it)
                            if(obj.getString("result")=="OK") {
                                Toast.makeText(
                                    view.context,
                                    "Password berhasil diubah",
                                    Toast.LENGTH_SHORT
                                ).show()
                                binding.editTextPassOld.text.clear()
                                binding.editTextPassNew.text.clear()
                                binding.editTextPassConfirm.text.clear()
                            }
                        },

                        {
                            Log.e("apiresult", it.message.toString())
                            Toast.makeText(view.context, "Gagal ubah password", Toast.LENGTH_SHORT).show()
                        }) {
                        override fun getParams(): MutableMap<String, String>? {
                            val params = HashMap<String, String>()
                            params["old_pass"] = oldPass
                            params["new_pass"] = newPass
                            params["user_id"] = id.toString()
                            return params
                        }
                    }
                    q.add(stringRequest)
                }
            }
            Toast.makeText(view.context,"Data Password tidak boleh kosong", Toast.LENGTH_SHORT).show()

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(user_id:Int) =
            PrefsFragment().apply {
                arguments = Bundle().apply {
                    putInt("user_id", user_id)
                }
            }
    }
}