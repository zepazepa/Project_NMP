package com.ubayaprojectnmp.cerbung

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayaprojectnmp.cerbung.databinding.FragmentHomeBinding
import org.json.JSONObject

class HomeFragment : Fragment() {
    var listCerbung: ArrayList<Cerbung> = arrayListOf()
    private lateinit var binding:FragmentHomeBinding
    private var user_id: Int? = null


    fun getCerbung(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421033/cerbung_getall.php"
        var stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result")=="OK"){
                    val dataUser = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Cerbung>>() { }.type
                    listCerbung = Gson().fromJson(dataUser.toString(), sType) as  ArrayList<Cerbung>
                    val lm = LinearLayoutManager(activity)
                    with(binding.recVIew){
                        layoutManager = lm
                        setHasFixedSize(true)
                        adapter = CerbungAdapter(listCerbung,user_id!!)
                    }
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
        getCerbung()
        arguments?.let {
            user_id = it.getInt("user_id")
        }
    }
    override fun onResume() {
        super.onResume()
        getCerbung()
        arguments?.let {
            user_id = it.getInt("user_id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(user_id:Int) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putInt("user_id", user_id)
                }
            }
    }
}