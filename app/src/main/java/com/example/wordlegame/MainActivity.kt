package com.example.wordlegame

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wordlegame.FourLetterWordList.getRandomFourLetterWord
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var textInput: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resetButton = findViewById<Button>(R.id.resetButton)
        val wordToGuess = getRandomFourLetterWord()
        val wordToGuessView = findViewById<TextView>(R.id.wordToGuessView)

        resetButton.visibility = View.GONE
        wordToGuessView.visibility = View.GONE
        wordToGuessView.text = wordToGuess

        val textInput = findViewById<EditText>(R.id.textInput)
        val guess1 = findViewById<TextView>(R.id.guess1)
        val guess1Check = findViewById<TextView>(R.id.guess1Check)
        val guess2 = findViewById<TextView>(R.id.guess2)
        val guess2Check = findViewById<TextView>(R.id.guess2Check)
        val guess3 = findViewById<TextView>(R.id.guess3)
        val guess3Check = findViewById<TextView>(R.id.guess3Check)
        val guessButton = findViewById<Button>(R.id.guessButton)

        fun View.hideKeyboard() {
            val inputManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(windowToken, 0)
        }

        guessButton.setOnClickListener {
            guess1.text = textInput.text
            guess1Check.text = checkGuess(guess1.text.toString(), wordToGuess)
            it.hideKeyboard()
            guessButton.setOnClickListener {
                guess2.text = textInput.text
                guess2Check.text = checkGuess(guess2.text.toString(), wordToGuess)
                it.hideKeyboard()
                guessButton.setOnClickListener {
                    guess3.text = textInput.text
                    guess3Check.text = checkGuess(guess3.text.toString(), wordToGuess)
                    it.hideKeyboard()
                    wordToGuessView.visibility = View.VISIBLE
                    Toast.makeText(
                        this@MainActivity,
                        "You have exceed your allowed number of guesses",
                        Toast.LENGTH_SHORT
                    ).show()
                    guessButton.setAlpha(.5f);
                    guessButton.setClickable(false);
                    resetButton.visibility = View.VISIBLE
                    resetButton.setOnClickListener {
                        val intent = intent
                        finish()
                        startActivity(intent)
                    }

                }
            }
        }


    }

    /**
     * Parameters / Fields:
     *   wordToGuess : String - the target word the user is trying to guess
     *   guess : String - what the user entered as their guess
     *
     * Returns a String of 'O', '+', and 'X', where:
     *   'O' represents the right letter in the right place
     *   '+' represents the right letter in the wrong place
     *   'X' represents a letter not in the target word
     */

    /** (Required Function)
    private fun checkGuess(guess: String, wordToGuess: String) : String {
    var result = ""
    for (i in 0..3) {
    if (guess[i] == wordToGuess[i]) {
    result += "O"
    }
    else if (guess[i] in wordToGuess) {
    result += "+"
    }
    else {
    result += "X"
    }
    }
    return result
    } */

    // "checkGuess Function" Modified with Spannable for Stretch Features
    private fun checkGuess(guess: String, wordToGuess: String): SpannableStringBuilder {
        val result = SpannableStringBuilder(guess)
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result.setSpan(
                    ForegroundColorSpan(Color.rgb(76, 153, 0)),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else if (guess[i] in wordToGuess) {
                result.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            } else {
                result.setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    i,
                    i + 1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return result
    }
}