package android.example.com.colormyviews

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
            boxOneText,
            boxTwoText,
            boxThreeText,
            boxFourText,
            boxFiveText,
            redButton,
            yellowButton,
            greenButton
        )
    }

    private fun setListeners() {
        model.forEach { it.setOnClickListener(::handleOnClick) }
    }

    private fun handleOnClick(view: View) = when (view.id) {
        R.id.boxOneText -> view.setBackgroundColor(Color.CYAN)
        R.id.boxTwoText -> view.setBackgroundColor(Color.MAGENTA)
        R.id.boxThreeText -> view.setBackgroundResource(android.R.color.holo_blue_bright)
        R.id.boxFourText -> view.setBackgroundResource(android.R.color.holo_red_light)
        R.id.boxFiveText -> view.setBackgroundResource(android.R.color.background_dark)

        // bottom buttons
        R.id.redButton -> boxThreeText.setBackgroundResource(R.color.red)
        R.id.yellowButton -> boxFiveText.setBackgroundResource(R.color.yellow)
        R.id.greenButton -> boxOneText.setBackgroundResource(R.color.green)

        else -> view.setBackgroundColor(Color.LTGRAY)
    }
}
