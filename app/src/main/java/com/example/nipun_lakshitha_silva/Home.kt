package com.example.nipun_lakshitha_silva

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase.getInstance
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.internal.Internal.instance

class Home : AppCompatActivity() {

    lateinit var ListView: ListView;
    lateinit var SearchView: SearchView;
    lateinit var toDoList: MutableList<String>;
    lateinit var toDoIdList: MutableList<String>;
    lateinit var database: FirebaseDatabase
    lateinit var data: DatabaseReference
    var count:Int = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar!!.hide()


        setContentView(R.layout.activity_home)


        //show To Do List
        showListView();


        //go to add new page
        val addIconImage: ImageView = findViewById(R.id.addIconImage);

        addIconImage.setOnClickListener {
            navigateAddNewPage();
        }


        //find the search view
        SearchView = findViewById(R.id.searchFeild);

    }

    fun navigateAddNewPage() {

        val intent = Intent(this, Add_New::class.java);
        intent.putExtra("count",count)
        startActivity(intent);
        finish();


    }

    fun showListView() {
        toDoList = mutableListOf()
        toDoIdList = mutableListOf();

        ListView = findViewById(R.id.listView)

        database = FirebaseDatabase.getInstance();
        data = database.getReference("Id")


        val intent = Intent(this, Detail::class.java);


        data.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                println(error)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (value in snapshot.children) {
                    toDoIdList.add(value.key.toString())
                    toDoList.add(value.value.toString())
                }
                count=toDoIdList.count()

                val idAdapter: ArrayAdapter<String> = ArrayAdapter(
                    this@Home, android.R.layout.simple_expandable_list_item_1, toDoList
                )
                ListView.adapter = idAdapter


                ListView.setOnItemClickListener { parent, view, position, id ->
                    intent.putExtra("ListId", toDoIdList[position])
                    intent.putExtra("fieldID", toDoList[position])
                    startActivity(intent);
                    finish();
                }

                SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        SearchView.clearFocus()
                        if (toDoList.contains(query)) {
                            idAdapter.filter.filter(query)
                        } else {
                            Toast.makeText(applicationContext, "To Do not found", Toast.LENGTH_LONG)
                                .show()
                        }

                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        idAdapter.filter.filter(newText)
                        return false
                    }

                })

            }

        })




    }


}