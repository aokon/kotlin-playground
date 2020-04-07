package com.elegantcode.button

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.elegantcode.button.R.id
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity(private var counter: Int = 0) : AppCompatActivity() {
    private fun timeSuffix(): String = resources.getQuantityString(R.plurals.times, counter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val helloTextView = findViewById<TextView>(id.text_view_id)

        btn_clicker.setOnClickListener {
            counter += 1
            Log.d("Clicker", "Current value $counter")
            // helloTextView.text = counter.toString()
            text_view_id.text = counter.toString()
        }

        btn_reset.setOnClickListener {
            Toast.makeText(getApplicationContext(), getString(
                R.string.message_onclicked,
                counter.toString(),
                timeSuffix()
            ), Toast.LENGTH_LONG).show()
            counter = 0
            text_view_id.text = getString(R.string.greeting_message)
        }
    }
}
