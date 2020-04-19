package pl.elegantcode.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doneBtn.setOnClickListener {
            if (nicknameEdit.text.isNotEmpty()) {
                nicknameText.text = nicknameEdit.text
                nicknameText.visibility = View.VISIBLE
                nicknameEdit.visibility = View.GONE
                it.visibility = View.GONE

                // hide the keyboard
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)

            } else {
                Toast.makeText(baseContext, "Sorry, You need to apply a nickname.", Toast.LENGTH_LONG).show()
            }
        }

        nicknameText.setOnClickListener {
            it.visibility = View.GONE
            nicknameEdit.visibility = View.VISIBLE
            nicknameEdit.requestFocus()
            doneBtn.visibility = View.VISIBLE

            // show the keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(nicknameEdit, 0)
        }
    }
}
