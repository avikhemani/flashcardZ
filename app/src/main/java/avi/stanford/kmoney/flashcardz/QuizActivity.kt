package avi.stanford.kmoney.flashcardz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*

class QuizActivity : AppCompatActivity() {

    private var wordnumber = 1
    private var flashcardmap = HashMap<String, String>()
    private var flashcardwords = ArrayList<String>()
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        flashcardname_label.text = intent.getStringExtra("name")
        flashcardmap = intent.getSerializableExtra("wordmap") as HashMap<String, String>
        flashcardwords = intent.getStringArrayListExtra("wordlist")

        updateUI()
    }

    fun enterWord(view: View) {
        val guess = enterWord_text.text.toString()
        if (guess == flashcardwords[wordnumber - 1]) {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            correctAnswers++
        } else {
            Toast.makeText(this, "Incorrect. Correct word was " +
                    "${flashcardwords[wordnumber - 1]}.", Toast.LENGTH_LONG).show()
        }
        wordnumber++
        resetUI()
        updateUI()
    }

    private fun endQuiz() {
        enter_button.isEnabled = false
        enterWord_text.visibility = View.INVISIBLE
        definition_label.text = "You got $correctAnswers correct out of ${flashcardwords.size} words!"
    }

    fun retryQuiz(view : View) {
        flashcardwords.shuffle()
        wordnumber = 1
        resetUI()
        updateUI()
    }

    fun updateUI() {
        if (wordnumber <= flashcardwords.size) {
            definition_label.text = flashcardmap[flashcardwords[wordnumber - 1]]
            enter_button.isEnabled = true
            enterWord_text.visibility = View.VISIBLE
        } else {
            endQuiz()
        }
    }

    fun resetUI() {
        enterWord_text.setText("")
        enterWord_text.hint = "Enter word"
    }

    fun mainMenu(view : View) {
        val intentToMainMenuActivity = Intent(this, MainMenuActivity::class.java)
        startActivity(intentToMainMenuActivity)
    }
}
