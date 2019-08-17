package avi.stanford.kmoney.flashcardz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val quote = "Successful people are not gifted. They just work hard, then succeed on purpose. - Anonymous"
        quote_label.text = quote
    }

    fun studyCards(view: View) {
        val intentToFlashcardsActivity = Intent(this, StudyCardsActivity::class.java)
        startActivity(intentToFlashcardsActivity)
    }

    fun createCards(view: View) {
        val intentToCreateCardsActivity = Intent(this, CreateCardsActivity::class.java)
        startActivity(intentToCreateCardsActivity)
    }
}
