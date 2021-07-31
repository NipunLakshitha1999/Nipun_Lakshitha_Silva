package com.example.nipun_lakshitha_silva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Add_New : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var data: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()


        setContentView(R.layout.activity_add__new)

        //go to previos page
        val btnBack:ImageView =findViewById(R.id.backiconImage)

        btnBack.setOnClickListener {
            navigateBackToHomePage();
        }

        // add to do
        val btnAddNew: Button =findViewById(R.id.btnAddNew)
        btnAddNew.setOnClickListener {
            addDataToFireStore();
        }
    }

    fun navigateBackToHomePage(){
        val intent= Intent(this,Home::class.java);
        startActivity(intent)
        finish()
    }


    fun addDataToFireStore(){

        val Edtext:EditText =findViewById(R.id.txtInputOfAddNew);

        val value:String = Edtext.text.toString();


        database = FirebaseDatabase.getInstance();
        data = database.getReference("Id")


        data.child(value).setValue(value).addOnSuccessListener {
            val builer=AlertDialog.Builder(this);
            builer.setTitle(" Success");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                val intent= Intent(this,Home::class.java);
                startActivity(intent);
                finish()
            }
            builer.show();

        }.addOnFailureListener(OnFailureListener {
            val builer=AlertDialog.Builder(this);
            builer.setTitle(" Faild ");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                finish();
                startActivity(intent)
            }
            builer.show()
        })

    }
}