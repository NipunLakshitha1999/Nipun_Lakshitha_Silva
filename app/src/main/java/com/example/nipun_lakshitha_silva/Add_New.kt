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
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Add_New : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()


        setContentView(R.layout.activity_add__new)

        val btnBack:ImageView =findViewById(R.id.backiconImage)

        btnBack.setOnClickListener {
            navigateBackToHomePage();
        }

        val btnAddNew: Button =findViewById(R.id.btnAddNew)
        btnAddNew.setOnClickListener {
            addDataToFireStore();
        }
    }

    fun navigateBackToHomePage(){
        val intent= Intent(this,Home::class.java);
        startActivity(intent)
    }
    fun addDataToFireStore(){

        val Edtext:EditText =findViewById(R.id.txtInputOfAddNew);

        val value:String = Edtext.text.toString();


        val db= Firebase.firestore;
        val data = hashMapOf("name" to value);
        db.collection("ToDo").document(value).set(data, SetOptions.merge()).addOnSuccessListener {
            val builer=AlertDialog.Builder(this);
            builer.setTitle(" Success");
            builer.setMessage("Press Ok and Continue")

            builer.setPositiveButton("OK"){dialogInterface,which ->
                val intent= Intent(this,Home::class.java);
                startActivity(intent);
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