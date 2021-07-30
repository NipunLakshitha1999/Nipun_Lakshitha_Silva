package com.example.nipun_lakshitha_silva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Edit : AppCompatActivity() {

    lateinit var id:String
    lateinit var fieldId:String
    lateinit var text: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()


        setContentView(R.layout.activity_edit)

        id= intent.getStringExtra("ListId").toString()
        fieldId=intent.getStringExtra("fieldID").toString()


        text= findViewById(R.id.txtInputOfEdit)
        text.setText(fieldId,TextView.BufferType.EDITABLE)

        val backBtn:ImageView = findViewById(R.id.backiconImageEdit)

        backBtn.setOnClickListener {
            navigateBackToHome();
        }

        val btnEdit:Button = findViewById(R.id.btnEdit)

        btnEdit.setOnClickListener {
            editTextAndUpdate()
        }

        val btnDelete:Button = findViewById(R.id.btnDelete)

        btnDelete.setOnClickListener {
            deleteField()
        }





    }

    fun navigateBackToHome(){
        val intent= Intent(this,Home::class.java);
        startActivity(intent)
        finish()
    }
    fun editTextAndUpdate(){
        val db= Firebase.firestore;

        val value:String = text.text.toString();
        val data = hashMapOf("id" to value);
        db.collection("ToDo").document(id).set(data, SetOptions.merge()).addOnSuccessListener {
            val builer= AlertDialog.Builder(this);
            builer.setTitle(" Update Success");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                val intent= Intent(this,Home::class.java);
                startActivity(intent);
                finish()
            }
            builer.show();

        }.addOnFailureListener(OnFailureListener {
            val builer= AlertDialog.Builder(this);
            builer.setTitle(" Update Faild ");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                finish();
                startActivity(intent)
            }
            builer.show()
        })

    }


    fun deleteField(){
        val db= Firebase.firestore;
        db.collection("ToDo").document(id).delete().addOnSuccessListener {
            val builer= AlertDialog.Builder(this);
            builer.setTitle(" Delete Success");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                val intent= Intent(this,Home::class.java);
                startActivity(intent);
                finish()
            }
            builer.show();

        }.addOnFailureListener(OnFailureListener {
            val builer= AlertDialog.Builder(this);
            builer.setTitle(" Delete Faild ");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                finish();
                startActivity(intent)
            }
            builer.show()
        })
    }
}

