package com.ubayaprojectnmp.cerbung

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.CerbungItemSimpleBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FollowAdapter(val listFollowCerbung:ArrayList<Cerbung>, val user_id:Int ): RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    class FollowViewHolder(val binding: CerbungItemSimpleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = CerbungItemSimpleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFollowCerbung.size
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        with(holder.binding){
            val img_url = listFollowCerbung[position].img_url
            val builder = Picasso.Builder(holder.itemView.context)
            builder.listener {picasso, url, exception -> exception.printStackTrace()}
            builder.build().load(img_url).into(ImagePoster)

            textTitle.setText(listFollowCerbung[position].title.toString())
            textAuthor.setText(listFollowCerbung[position].name.toString())
            var waktu_dibuat = listFollowCerbung[position].waktu_dibuat
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val waktu = LocalDateTime.parse(waktu_dibuat, formatter)
            textInfo.setText(waktu.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")).toString())
            cardCerbung.setOnClickListener(){
                val id = listFollowCerbung[position].idcerbung
                val intent = Intent(it.context,ReadActivity::class.java)
                intent.putExtra("cerbung_id",id)
                intent.putExtra("user_id",user_id)
                it.context.startActivity(intent)
            }
        }
    }
}