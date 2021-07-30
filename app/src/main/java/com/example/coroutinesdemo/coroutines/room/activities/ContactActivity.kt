package com.example.coroutinesdemo.coroutines.room.activities
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.coroutinesdemo.R
import com.example.coroutinesdemo.coroutines.room.database.ContactDatabase
import com.example.coroutinesdemo.coroutines.room.entity.Contact
import com.example.coroutinesdemo.coroutines.room.utils.RoomUtils.database
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        database = ContactDatabase.getDatabase(this)
        performCrud()
    }

    private fun updateData() {
        Toast.makeText(
            this@ContactActivity,
            "You have deleted entity",
            Toast.LENGTH_SHORT
        ).show()
        updateFab.setOnClickListener {
            Toast.makeText(
                this@ContactActivity,
                "You have updated data",
                Toast.LENGTH_SHORT
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                database.contactDao().updateByID(777, 22)
            }
        }
    }

    private fun deleteAllData() {
        deleteFab.setOnClickListener {
            Toast.makeText(
                this@ContactActivity,
                "You have deleted all data",
                Toast.LENGTH_SHORT
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                //Deleting all database
                database.contactDao().deleteAll()
                //Deleting by Id
                database.contactDao().deleteById(22)
                //Deleting by name
                database.contactDao().removeByName("glass")
            }
        }
    }
    private fun addData() {
        addFab.setOnClickListener {
            Toast.makeText(this@ContactActivity, "You have added one Entry", Toast.LENGTH_SHORT)
                .show()
            CoroutineScope(Dispatchers.IO).launch {
                var input = edtName.text.toString()
                database.contactDao().insertData(Contact(12, input, 777))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun performCrud() {
        addData()
        deleteAllData()
        updateData()
        database.contactDao().getContact().observe(this, Observer {
            database.contactDao().getContact()
            it.map {
                tvDisplay.text = it.name + " " + it.phone + " " + it.id
            }
        })
    }
}






