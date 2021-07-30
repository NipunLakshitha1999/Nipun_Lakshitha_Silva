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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Detail : AppCompatActivity() {

    lateinit var id:String
    lateinit var fieldId:String
    lateinit var text: EditText

    lateinit var database: FirebaseDatabase
    lateinit var data: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()


        setContentView(R.layout.activity_detail)

        //get to do list id from home page
        id= intent.getStringExtra("ListId").toString()

        //get to do list field values from home page
        fieldId=intent.getStringExtra("fieldID").toString()


        //set text to edit text
        text= findViewById(R.id.txtInputOfDetail)

        if (intent.getStringExtra("editValue") != null){
            val changeText= intent.getStringExtra("editValue")
            text.setText(changeText,TextView.BufferType.SPANNABLE)
        }else{
            text.setText(id,TextView.BufferType.EDITABLE)
        }


        //back to home
        val backBtn:ImageView = findViewById(R.id.backiconImageDetail)

        backBtn.setOnClickListener {
            navigateBackToHome();
        }

        //navigate to edit page
        val btnEdit:Button = findViewById(R.id.btnEdit)

        btnEdit.setOnClickListener {
            navigateToEditPage()
        }

        //delete field
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


    fun navigateToEditPage(){
        val value= text.text.toString()
        val intent= Intent(this,Edit::class.java);
        intent.putExtra("id",id)
        intent.putExtra("fieldId",value)
        startActivity(intent)
        finish()

    }


    fun deleteField(){
        database = FirebaseDatabase.getInstance();
        data = database.getReference("Id")

        val value= text.text.toString()
        data.child(value).removeValue().addOnSuccessListener {
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

