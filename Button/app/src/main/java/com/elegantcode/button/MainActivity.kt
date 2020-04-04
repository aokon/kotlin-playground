package com.elegantcode.button

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity(counter: Int = 0) : AppCompatActivity() {
    private var counter = counter
    private fun timeSuffix(): String = resources.getQuantityString(R.plurals.times, counter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val helloTextView: TextView = findViewById(R.id.text_view_id);
        helloTextView.text = getString(R.string.greeting_message)

        btn_clicker.setOnClickListener {
            counter += 1
            helloTextView.text = getString(
                R.string.message_onclicked,
                counter.toString(),
                timeSuffix()
            )
        }

        btn_reset.setOnClickListener {
            counter = 0
            helloTextView.text = getString(R.string.greeting_message)
        }
    }
}
