package com.ubayaprojectnmp.cerbung

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.CerbungItemBinding

class CerbungAdapter(): RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {
    class CerbungViewHolder(val binding: CerbungItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CerbungViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Global.cerbung.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        var id = position
        var url = Global.cerbung[id].imgurl
        with(holder.binding){
            var builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, url, exception -> exception.printStackTrace() }
            builder.build().load(url).into(ImagePoster)
            textTitle.setText(Global.cerbung[id].title)
            var author = "by "+ Global.cerbung[id].creator
            textAuthor.setText(author)
            textSynopsis.setText(Global.cerbung[id].sinopsis)
            var info = "par: ${Global.cerbung[id].num_paragraf.toString()}; Like: ${Global.cerbung[id].like.toString()}"
            textInfo.setText(info)

            buttonRead.setOnClickListener {
                val intent = Intent(it.context,ReadActivity::class.java)
                intent.putExtra("ID",id)
                it.context.startActivity(intent)

            }
        }
    }
}