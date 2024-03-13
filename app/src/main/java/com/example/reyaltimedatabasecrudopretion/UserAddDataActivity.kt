package com.example.reyaltimedatabasecrudopretion

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reyaltimedatabasecrudopretion.Adapter.UserDataModle
import com.example.reyaltimedatabasecrudopretion.databinding.UserAddDataActivityBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class UserAddDataActivity : AppCompatActivity() {

    private lateinit var binding: UserAddDataActivityBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserAddDataActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uplodeData.setOnClickListener {
            uplodeData()
        }
    }

    private fun uplodeData() {
        val userName = binding.userNameEditText.text.toString()
        val userEmail = binding.userEmailEditText.text.toString()
        val cheating_id = "cheating" + generateRandomString(20)

        databaseReference = FirebaseDatabase.getInstance().getReference("student")
        val userData = UserDataModle(userName, userEmail, cheating_id)
        databaseReference.child(cheating_id).setValue(userData)
            .addOnSuccessListener {
                startActivity(Intent(this, HomeActivity::class.java))
                Toast.makeText(this, "Add Data Success", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "data fled", Toast.LENGTH_SHORT).show()
            }
    }

    private fun generateRandomString(length: Int): String? {
        val uuid = UUID.randomUUID()
        return uuid.toString().substring(0, length)
    }
}