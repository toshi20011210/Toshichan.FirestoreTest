package app.takahashi.toshichan.firestoretest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.takahashi.toshichan.firestoretest.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        // Firestoreをインスタンス化
        val db = Firebase.firestore

        // ボタンを押したときの処理
        binding.sendButton.setOnClickListener {
            // インスタンス化
            val dataToSend = Datas(
                //date = nothing for now
                name = binding.nameInput.text.toString(),
                text = binding.textInput.text.toString()
            )
            db.collection("log")
                .add(dataToSend)
                .addOnSuccessListener { documentReference ->
                    Log.d("SUCCESS", "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(applicationContext,"Successfully Sent!", Toast.LENGTH_SHORT).show()
                    binding.nameInput.setText("")
                    binding.textInput.setText("")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(applicationContext,"Error! Check Connection and Try Again!", Toast.LENGTH_SHORT).show()
                    Log.w("FAIL", "Error adding document", e)
                }
        }
    }
}