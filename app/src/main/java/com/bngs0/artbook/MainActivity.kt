package com.bngs0.artbook

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bngs0.artbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database : SQLiteDatabase
    private lateinit var artList : ArrayList<Art>
    private lateinit var artAdapter : ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        artList = ArrayList<Art>()

        //recyclerview
        artAdapter = ArtAdapter(artList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = artAdapter


        //db
        database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null)
        val cursor = database.rawQuery("SELECT * FROM arts",null)
        val idIndex = cursor.getColumnIndex("id")
        val artNameIndex = cursor.getColumnIndex("artname")

        while (cursor.moveToNext()){
            val name = cursor.getString(artNameIndex)
            val id = cursor.getInt(idIndex)

            val art = Art(id,name)
            artList.add(art)
        }
        artAdapter.notifyDataSetChanged()

        cursor.close()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // menu ile art_menu birbirine bağlandı
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.art_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_art){
            val intent = Intent(this@MainActivity, ArtActivity::class.java)
            intent.putExtra("info","new") // sanat eklemeye geldim
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}