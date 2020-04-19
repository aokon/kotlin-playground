package pl.elegantcode.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        done_button.setOnClickListener {
            nickname_text.text = name_edit.text
            nickname_text.visibility = View.VISIBLE
            name_edit.visibility = View.GONE
            it.visibility = View.GONE

            // hide the keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }

        nickname_text.setOnClickListener {
            it.visibility = View.GONE
            name_edit.visibility = View.VISIBLE
            name_edit.requestFocus()
            done_button.visibility = View.VISIBLE

            // show the keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(name_edit, 0)
        }
    }
}
