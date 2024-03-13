package com.example.reyaltimedatabasecrudopretion

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reyaltimedatabasecrudopretion.Adapter.OnClickListener
import com.example.reyaltimedatabasecrudopretion.Adapter.UserDataAdapter
import com.example.reyaltimedatabasecrudopretion.Adapter.UserDataModle
import com.example.reyaltimedatabasecrudopretion.databinding.HomeActivityBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity(),OnClickListener {
    private lateinit var binding: HomeActivityBinding
    private lateinit var databaseReference: DatabaseReference
    lateinit var userList: ArrayList<UserDataModle>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userList = arrayListOf()

        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getUserData()

        binding.goUplodeDataButton.setOnClickListener {
            startActivity(Intent(this, UserAddDataActivity::class.java))
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")

    private fun getUserData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("student")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    var list = ArrayList<UserDataModle>()
                    for (postSnapshot in snapshot.children){
                        val value = postSnapshot.getValue(UserDataModle::class.java)
                        value?.let { list.add(it) }
                        val userAdapter = UserDataAdapter(this@HomeActivity,list,this@HomeActivity)
                        binding.userListRecycleView.layoutManager = LinearLayoutManager(this@HomeActivity)
                        binding.userListRecycleView.adapter = userAdapter
                    }
                    userList.addAll(list)

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }



    override fun onUpdateStudentData(position: Int) {
        val intent = Intent(this,UpdateActivity::class.java)
        intent.putExtra("userName",userList[position].userName)
        intent.putExtra("userEmail",userList[position].userEmail)
        intent.putExtra("userId",userList[position].userId)
        startActivity(intent)
        finish()
    }

    override fun onDeleteStudentData(position: Int) {
        val studentId = userList[position].userId
       databaseReference.child(studentId!!).removeValue()
           .addOnSuccessListener {
               Toast.makeText(this, "Student data delete successful", Toast.LENGTH_SHORT).show()
           }
            .addOnFailureListener {
                Toast.makeText(this, "Student data delete failed", Toast.LENGTH_SHORT).show()
            }
    }

}


