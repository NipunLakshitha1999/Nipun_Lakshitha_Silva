package com.example.nipun_lakshitha_silva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Home : AppCompatActivity() {
    private lateinit var list : ArrayList<String>
    private lateinit var ListView: ListView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()


        setContentView(R.layout.activity_home)
        ListView = findViewById(R.id.listView);
        list = arrayListOf();
        val db = Firebase.firestore;
        db.collection("ToDo").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    println(error)
                }

                for (dc : DocumentChange in value!!.documentChanges){
                    println(dc.document.id)
                    list.add(dc.document.id)

                }
            }

        })

        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_expandable_list_item_1,list
        )
        ListView.adapter = arrayAdapter;
        ListView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,"Item Selected"+list[position],Toast.LENGTH_LONG).show()
        }
        showListView();

        val addIconImage: ImageView = findViewById(R.id.addIconImage);







        addIconImage.setOnClickListener {
            navigateAddNewPage();
        }


    }

    fun navigateAddNewPage() {
        val intent = Intent(this, Add_New::class.java);
        startActivity(intent);


    }

    fun showListView(){

        list = arrayListOf();
        val db = Firebase.firestore;
        db.collection("ToDo").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null){
                    println(error)
                }

                for (dc : DocumentChange in value!!.documentChanges){
                    println(dc.document.id)
                    list.add(dc.document.id)

                }
            }

        })

        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_expandable_list_item_1,list
        )
        ListView.adapter = arrayAdapter;
        ListView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,"Item Selected"+list[position],Toast.LENGTH_LONG).show()
        }

    }

}