package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TableRow
import kotlinx.android.synthetic.main.activity_main.*
import pl.rebased.fiften.Puzzle
import kotlin.math.abs
import kotlin.math.absoluteValue

// TODO: make a new game
// TODO: store state via onSaveInstanceState
// TODO: Verify if the button has valid position
// TODO: Start a new game when game was finished
class MainActivity : AppCompatActivity() {
    lateinit var zeroBtn: Button
    private val puzzleSize = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Don't make complicated calculations in UI thread
        // Don't make async operation
        runOnUiThread {
            mixAppButtons(puzzleSize)
            hideZero()
        }

        allButton().forEach {
            it.setOnClickListener(::handleBtnClick)
        }
    }

    private fun handleBtnClick(v: View) {
       val button = v as Button
        if (!canSwap(button)) return

        val previousText = button.text
        button.text = "0"
        zeroBtn.text = previousText
        hideZero()
    }

    private fun canSwap(button: Button): Boolean {
        var xPositionDelta = (button.x - zeroBtn.x).absoluteValue.toInt()
        var yPositionDelta = ((button.parent as TableRow).y - (zeroBtn.parent as TableRow).y).absoluteValue.toInt()
        val delta = xPositionDelta + yPositionDelta
        return delta <= button.width || delta <= button.height
    }

    private fun mixAppButtons(i: Int) {
       val puzzle = Puzzle(i).asList()

        allButton().zip(puzzle).forEach { (button , it) ->
            button.text = it.toString()
        }
    }

    private fun hideZero() {
        zeroBtn = allButton().find { it.text == "0" }!!

        allButton().forEach { it.visibility = View.VISIBLE }

        zeroBtn.visibility = View.INVISIBLE
    }

    private fun allButton(): List<Button> {
        return listOf(
            button00, button01, button02, // button03,
            button10, button11, button12, // button13,
            button20, button21, button22 // button23,
            // button30, button31, button32, button33
        )
    }
}
