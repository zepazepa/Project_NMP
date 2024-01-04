package com.ubayaprojectnmp.cerbung

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ubayaprojectnmp.cerbung.databinding.FragmentCreateBinding
import com.ubayaprojectnmp.cerbung.databinding.FragmentHomeBinding

class CreateFragment : Fragment() {
    private lateinit var binding: FragmentCreateBinding
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
        binding = FragmentCreateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.setOnClickListener(){
            val intent = Intent(activity,CreateActivity::class.java)
            intent.putExtra("user_id",user_id)
            startActivity(intent)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(user_id:Int) =
            CreateFragment().apply {
                arguments = Bundle().apply {
                    putInt("user_id", user_id)
                }
            }
    }
}