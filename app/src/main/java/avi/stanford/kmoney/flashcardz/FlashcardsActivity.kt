package avi.stanford.kmoney.flashcardz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_flashcards.*
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent

class FlashcardsActivity : AppCompatActivity() {

    private var cardnumber = 1
    private var isWord = true
    private var flashcardmap = HashMap<String, String>()
    private var flashcardwords = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards)

        val flashcard = intent.getStringExtra("flashcardname")
        flashcardname_label.text = flashcard

        val textfile = "$flashcard.txt"
        populateModel(textfile)

        updateUI()
    }

    private fun populateModel(textfile : String) {
        val reader = Scanner(openFileInput(textfile))
        while (reader.hasNextLine()) {
            val line = reader.nextLine()

            val pieces = line.split("\t")
            if (pieces.size >= 2) {
                flashcardmap[pieces[0]] = pieces[1]
            }
        }

        val wordSet = flashcardmap.keys
        for(word in wordSet) {
            flashcardwords.add(word)
        }
    }

    fun cardClick(view : View) {
        isWord = !isWord
        updateUI()
    }

    fun rightCard(view : View) {
        cardnumber++
        isWord = true
        updateUI()
    }

    fun leftCard(view : View) {
        cardnumber--
        isWord = true
        updateUI()
    }

    fun mainMenu(view : View) {
        val intentToMainMenuActivity = Intent(this, MainMenuActivity::class.java)
        startActivity(intentToMainMenuActivity)
    }

    fun takeQuiz(view : View) {
        val intentToQuizActivity = Intent(this, QuizActivity::class.java)
        intentToQuizActivity.putExtra("wordmap", flashcardmap)
        intentToQuizActivity.putExtra("name", flashcardname_label.text.toString())
        intentToQuizActivity.putExtra("wordlist", flashcardwords)
        startActivity(intentToQuizActivity)
    }

    fun shuffleCards(view : View) {
        flashcardwords.shuffle()
        cardnumber = 1
        isWord = true
        updateUI()
    }

    fun updateUI() {
        left_button.isEnabled = true
        right_button.isEnabled = true
        if (cardnumber == 1) {
            left_button.isEnabled = false
        }
        if (cardnumber == flashcardwords.size) {
            right_button.isEnabled = false
        }

        if (isWord) {
            flashcard_button.text = flashcardwords[cardnumber - 1]
        } else {
            flashcard_button.text = flashcardmap[flashcardwords[cardnumber - 1]]
        }

        cardnumber_label.text = "$cardnumber of ${flashcardwords.size}"
    }
}
