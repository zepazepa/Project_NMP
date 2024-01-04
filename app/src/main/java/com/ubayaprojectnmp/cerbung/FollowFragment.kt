package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ubayaprojectnmp.cerbung.databinding.FragmentFollowBinding
import org.json.JSONObject

class FollowFragment : Fragment() {
    private lateinit var binding:FragmentFollowBinding
    var listFollowedCerbung: ArrayList<Cerbung> = arrayListOf()
    var users_id: Int? = null
    fun loadFollowedCerbung(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421033/cerbung_getfollow.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Cerbung>>() { }.type
                    listFollowedCerbung = Gson().fromJson(data.toString(), sType) as ArrayList<Cerbung>
                    Log.d("apiresult", listFollowedCerbung.toString())

                    val lm = LinearLayoutManager(activity)
                    with (binding.recyclerView) {
                        layoutManager = lm
                        setHasFixedSize(true)
                        adapter = FollowAdapter(listFollowedCerbung,users_id!!)
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["users_id"] = users_id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            users_id = it.getInt("users_id")
        }
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            users_id = it.getInt("users_id")
        }
        loadFollowedCerbung()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root

    }

    companion object {
        @JvmStatic
        fun newInstance(users_id: Int) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt("users_id", users_id)
                }
            }
    }
}