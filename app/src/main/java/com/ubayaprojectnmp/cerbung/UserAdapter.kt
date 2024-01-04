package com.ubayaprojectnmp.cerbung

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubayaprojectnmp.cerbung.databinding.CerbungItemBinding
import com.ubayaprojectnmp.cerbung.databinding.ParagrafItemBinding
import com.ubayaprojectnmp.cerbung.databinding.UserItemBinding

class UserAdapter(val listUser: ArrayList<User>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    class UserViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserAdapter.UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder.binding){
            var img_url = listUser[position].url_photo
            var builder = Picasso.Builder(holder.itemView.context)
            builder.listener { picasso, uri, exception -> exception.printStackTrace() }
            builder.build().load(img_url).into(imageProfilePic)

            textNama.setText(listUser[position].name)
        }
    }
}