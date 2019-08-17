package avi.stanford.kmoney.flashcardz

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_cards.*
import java.io.PrintStream

class CreateCardsActivity : AppCompatActivity() {

    private val FLASHCARDS_LIST = "flashcardslisttest.txt"
    private var NEW_FLASHCARDS_NAME = ""
    private var currentWords = ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_cards)

        myAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1, currentWords)
        currentwords_listview.adapter = myAdapter
    }

    fun enterName(view : View) {
        val name = name_edittext.text.toString()
        if (name != "") {
            val outStream = PrintStream(openFileOutput(FLASHCARDS_LIST, Context.MODE_APPEND))
            outStream.println(name)
            outStream.close()

            val line = "$name.txt"

            NEW_FLASHCARDS_NAME = line
            name_edittext.visibility = View.INVISIBLE
            entername_button.visibility = View.INVISIBLE
            cardsname_label.text = name
            cardsname_label.visibility = View.VISIBLE
            word_edittext.visibility = View.VISIBLE
            definition_edittext.visibility = View.VISIBLE
            addword_button.visibility = View.VISIBLE
            done_button.visibility = View.VISIBLE
        } else {
            Toast.makeText(this, "You didn't enter a name!", Toast.LENGTH_LONG).show()
        }

    }

    fun addWord(view : View) {
        val word = word_edittext.text.toString()
        val definition = definition_edittext.text.toString()
        val line = "$word\t$definition"
        val lineToShow = "$word: \t$definition"

        word_edittext.setText("")
        word_edittext.hint = "Word"
        definition_edittext.setText("")
        definition_edittext.hint = "Definition"

        val outStream = PrintStream(openFileOutput(NEW_FLASHCARDS_NAME, Context.MODE_APPEND))
        outStream.println(line)
        outStream.close()

        currentWords.add(lineToShow)
        myAdapter.notifyDataSetChanged()
    }

    fun doneClick(view : View) {
        val myIntent = Intent(this, MainMenuActivity::class.java)
        startActivity(myIntent)
    }

}
