package avi.stanford.kmoney.flashcardz

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_study_cards.*
import java.util.*
import java.io.File
import java.io.PrintStream


class StudyCardsActivity : AppCompatActivity() {

    private val FLASHCARDS_LIST = "flashcardslisttest.txt"
    var flashcardlist = ArrayList<String>()
    private var flashcard = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_cards)

        populateArrayList()

        if (flashcardlist.size == 0) {
            Toast.makeText(this, "You haven't made any flashcards yet!", Toast.LENGTH_LONG).show()
        }

        val myAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1, flashcardlist
        )
        flashcard_listview.adapter = myAdapter

        flashcard_listview.setOnItemClickListener { _, _, index, _ ->
            val intentToFlashcardsActivity = Intent(this, FlashcardsActivity::class.java)
            intentToFlashcardsActivity.putExtra("flashcardname", flashcardlist[index])
            startActivity(intentToFlashcardsActivity)
            true
        }

        flashcard_listview.setOnItemLongClickListener { _, _, index, _ ->
            val adb = AlertDialog.Builder(this)
            adb.setTitle("Delete?")
            adb.setMessage("Are you sure you want to delete ${flashcardlist[index]}?")
            adb.setNegativeButton("Cancel", null)

            adb.setPositiveButton("Ok") { _, _ ->
                val flashcardToDelete = flashcardlist[index]
                flashcardlist.removeAt(index)
                myAdapter.notifyDataSetChanged()
                File(filesDir, "$flashcardToDelete.txt").delete()
                updateFlashcardList()
            }
            adb.show()
            true
        }
    }

    private fun populateArrayList() {
        val reader = Scanner(openFileInput(FLASHCARDS_LIST))
        while (reader.hasNextLine()) {
            val line = reader.nextLine()

            if (line != "") {
                flashcardlist.add(line)
            }
        }
    }

    private fun updateFlashcardList() {
        File(filesDir, FLASHCARDS_LIST).delete()
        val outStream = PrintStream(openFileOutput(FLASHCARDS_LIST, Context.MODE_APPEND))
        for (list in flashcardlist) {
            outStream.println(list)
        }
        outStream.close()
    }
}
