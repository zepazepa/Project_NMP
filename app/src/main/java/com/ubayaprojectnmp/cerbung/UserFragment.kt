package com.ubayaprojectnmp.cerbung

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
import com.ubayaprojectnmp.cerbung.databinding.FragmentUserBinding
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding

    var listUser:ArrayList<User> = arrayListOf()
    fun getUsers(){
        val q = Volley.newRequestQueue(activity)
        val url = "https://ubaya.me/native/160421033/user_getall.php"
        var stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if(obj.getString("result")=="OK"){
                    val dataUser = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<User>>() { }.type
                    listUser = Gson().fromJson(dataUser.toString(), sType) as  ArrayList<User>
                    val lm = LinearLayoutManager(activity)
                    with(binding.recyclerView){
                        layoutManager = lm
                        setHasFixedSize(true)
                        adapter = UserAdapter(listUser)
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
        getUsers()
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            UserFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}