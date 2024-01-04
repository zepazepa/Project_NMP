package com.ubayaprojectnmp.cerbung

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.CerbungItemBinding

class CerbungAdapter(val listCerbung:ArrayList<Cerbung>, val user_id:Int): RecyclerView.Adapter<CerbungAdapter.CerbungViewHolder>() {
    class CerbungViewHolder(val binding: CerbungItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CerbungViewHolder {
        val binding = CerbungItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CerbungViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listCerbung.size
    }

    override fun onBindViewHolder(holder: CerbungViewHolder, position: Int) {
        with(holder.binding){
            var img_url = listCerbung[position].img_url
            var builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            builder.build().load(img_url).into(ImagePoster)
            textTitle.setText(listCerbung[position].title)
            var author = "by ${listCerbung[position].name}"
            textAuthor.setText(author)
            textSynopsis.setText(listCerbung[position].description)
            txtLike.setText(listCerbung[position].like.toString())
            textInfo.setText(listCerbung[position].num_paragraf.toString())

            buttonRead.setOnClickListener {
                val id = listCerbung[position].idcerbung
                val intent = Intent(it.context,ReadActivity::class.java)
                intent.putExtra("cerbung_id",id)
                intent.putExtra("user_id",user_id)
                it.context.startActivity(intent)

            }
        }
    }
}