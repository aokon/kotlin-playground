package android.example.com.colormyviews

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }

    private val model by lazy {
        listOf(
            box_one_text,
            box_two_text,
            box_three_text,
            box_four_text,
            box_five_text
        )
    }

    private fun setListeners() {
        model.forEach { it.setOnClickListener(::handleOnClick) }
    }

    private fun handleOnClick(view: View) = when (view.id) {
        R.id.box_one_text -> view.setBackgroundColor(Color.CYAN)
        R.id.box_two_text -> view.setBackgroundColor(Color.MAGENTA)
        R.id.box_three_text -> view.setBackgroundResource(android.R.color.holo_blue_bright)
        R.id.box_four_text -> view.setBackgroundResource(android.R.color.holo_red_light)
        R.id.box_five_text -> view.setBackgroundResource(android.R.color.background_dark)
        else -> view.setBackgroundColor(Color.LTGRAY)
    }
}
