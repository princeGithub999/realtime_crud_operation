package com.example.reyaltimedatabasecrudopretion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reyaltimedatabasecrudopretion.Adapter.UserDataModle
import com.example.reyaltimedatabasecrudopretion.databinding.ActivityUpdateBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class UpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateBinding
    private lateinit var databaseReference: DatabaseReference
    private var userId:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)


        databaseReference = FirebaseDatabase.getInstance().getReference("student")
        val name = intent.getStringExtra("userName")
        val email = intent.getStringExtra("userEmail")
        userId = intent.getStringExtra("userId")

        binding.userNameUpdateEditText.setText(name)
        binding.userEmailUpdateEditText.setText(email)

        binding.updateUserData.setOnClickListener {
            updateData()
        }

    }
    private fun updateData(){
        val userName = binding.userNameUpdateEditText.text.toString()
        val userEmail = binding.userEmailUpdateEditText.text.toString()

            val updateData = UserDataModle(userName, userEmail,userId)
            databaseReference.child(userId!!).setValue(updateData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Student data update", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Student data failed", Toast.LENGTH_SHORT).show()
                }

    }
}


