package com.elegantcode.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private fun String.toEditable(): Editable? = Editable.Factory.getInstance().newEditable(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        additionBtn.setOnClickListener {
            val  result = operandOne.text.toString().toDouble() + operandTwo.text.toString().toDouble()
            resultText.text = result.toString()
        }

        substractionBtn.setOnClickListener {
            val  result = operandOne.text.toString().toDouble() - operandTwo.text.toString().toDouble()
            resultText.text = result.toString()
        }

        multiplicationBtn.setOnClickListener {
            val  result = operandOne.text.toString().toDouble() * operandTwo.text.toString().toDouble()
            resultText.text = result.toString()
        }

        divisionBtn.setOnClickListener {
            val  result = operandOne.text.toString().toDouble() / operandTwo.text.toString().toDouble()
            resultText.text = result.toString()
        }

        clearBtn.setOnClickListener {
            operandOne.text = "".toEditable()
            operandTwo.text = "".toEditable()
            resultText.text = "0.00"
            operandOne.requestFocus()
            Toast.makeText(applicationContext, "Operands values were deleted. Let's try again", Toast.LENGTH_LONG).show()
        }
    }
}

