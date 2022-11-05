package app.takahashi.toshichan.firestoretest

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.takahashi.toshichan.firestoretest.databinding.ActivityAddBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        binding = ActivityAddBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        //intent受け渡し
        //val collectionName = intent.getStringExtra("collectionName").toString()
        val collectionName = "thread one"

        // Firestoreをインスタンス化
        val db = Firebase.firestore

        //今入っている要素数
        var count = 0
        var userId = 0

        // ボタンを押したときの処理
        binding.sendButton.setOnClickListener {

            //現在のコレクションの要素数を調べる　（IDをふるため）
            db.collection(collectionName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        count = count + 1
                    }
                    Log.w("countO",count.toString())
                    //set ID
                    Log.w("countN",count.toString())
                    userId = count + 1
                    Log.w("USER_ID",userId.toString())
                    //data class
                    val dataToSend = Datas(
                        //date = nothing for now
                        name = binding.nameInput.text.toString(),
                        text = binding.textInput.text.toString()
                    )
                    //collection name
                    db.collection(collectionName).document(userId.toString())
                        .set(dataToSend)
                        .addOnSuccessListener { documentReference ->
                            Log.d("SUCCESS", "Document Added")
                            Toast.makeText(applicationContext, "Successfully Sent!", Toast.LENGTH_SHORT)
                                .show()

                            val intent = Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                applicationContext,
                                "Error! Check Connection and Try Again!",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.w("FAIL", "Error adding document", e)
                        }
                }
                .addOnFailureListener { exception ->
                    //Log.w(ContentValues.TAG, "Error getting documents."+count.toString(), exception)
                }


        }
    }
}