package com.example.reyaltimedatabasecrudopretion.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reyaltimedatabasecrudopretion.R

class UserDataAdapter(
    private val context: Context,
    private val userList: List<UserDataModle>,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<UserDataAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userName: TextView = itemView.findViewById(R.id.userName_textView)
        var userEmail: TextView = itemView.findViewById(R.id.userEmail_textView)
        var userDeleteButton: ImageButton = itemView.findViewById(R.id.deleteData_Button)
        var userUpdateButton: ImageButton = itemView.findViewById(R.id.updateData_Button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userName.text = userList[position].userName
        holder.userEmail.text = userList[position].userEmail


        holder.userUpdateButton.setOnClickListener {
                onClickListener.onUpdateStudentData(position)
        }
        holder.userDeleteButton.setOnClickListener {
            onClickListener.onDeleteStudentData(position)
        }
    }

}

interface OnClickListener {
    fun onUpdateStudentData(position: Int)
    fun onDeleteStudentData(position: Int)
}