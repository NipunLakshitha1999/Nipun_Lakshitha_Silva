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
//    private lateinit var list : ArrayList<String>
//    private lateinit var ListView: ListView


//    lateinit var ref: Firebase
    lateinit var ListView: ListView;
    lateinit var toDoList:MutableList<String>;
    lateinit var toDoIdList:MutableList<String>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()





        setContentView(R.layout.activity_home)

        showListView();






        val addIconImage: ImageView = findViewById(R.id.addIconImage);

        addIconImage.setOnClickListener {
            navigateAddNewPage();
        }


    }

    fun navigateAddNewPage() {

        val intent = Intent(this, Add_New::class.java);
        startActivity(intent);
        finish();


    }

    fun showListView(){
        toDoList = mutableListOf()
        toDoIdList= mutableListOf();
        ListView = findViewById(R.id.listView)

        val db = Firebase.firestore;
        val intent = Intent(this, Edit::class.java);
        db.collection("ToDo").addSnapshotListener(object :EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {


                if (error !=null){
                    println(error)
                }

                if (value!!.documents != null){
                    toDoList.clear()
                    for (e in value.documentChanges){
                        toDoIdList.add(e.document.id);
                        toDoList.add(e.document.get("id").toString());
                    }

                    val adapter: ArrayAdapter<String> = ArrayAdapter(
                        this@Home,android.R.layout.simple_expandable_list_item_1,toDoList
                    )
                    ListView.adapter=adapter

                    ListView.setOnItemClickListener { parent, view, position, id ->
                        intent.putExtra("ListId",toDoIdList[position])
                        intent.putExtra("fieldID",toDoList[position])
                        startActivity(intent);
                        finish();
                    }
                }
            }

        })

    }

}