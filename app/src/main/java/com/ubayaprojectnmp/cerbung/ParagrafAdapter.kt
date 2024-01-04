package com.ubayaprojectnmp.cerbung

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.ubayaprojectnmp.cerbung.databinding.CerbungItemBinding
import com.ubayaprojectnmp.cerbung.databinding.ParagrafItemBinding

class ParagrafAdapter(val listParagraf:ArrayList<Paragraf>): RecyclerView.Adapter<ParagrafAdapter.ParagrafViewHolder>() {
    class ParagrafViewHolder(val binding: ParagrafItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParagrafAdapter.ParagrafViewHolder {
        val binding = ParagrafItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ParagrafViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return listParagraf.size
    }
    override fun onBindViewHolder(holder: ParagrafViewHolder, position: Int) {
        with(holder.binding) {
            textParagraf.text = listParagraf[position].isi
            textPenulis.text = listParagraf[position].name
            btnLike.setOnClickListener {
                val user_id = listParagraf[position].users_id
                val par_id = listParagraf[position].idpar
                if (btnLike.tag == "LIKE") {
                    btnLike.setImageResource(R.drawable.icon_notlike)
                    btnLike.tag = "NOTLIKE"
                } else if (btnLike.tag == "NOTLIKE") {
                    btnLike.setImageResource(R.drawable.icon_like)
                    btnLike.tag = "LIKE"
                }
            }
        }
    }

}