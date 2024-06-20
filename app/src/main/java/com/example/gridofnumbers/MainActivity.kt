package com.example.gridofnumbers

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gridofnumbers.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val numbers = (1..100).toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        populateGrid()
        spinnerSetup()
    }


    private fun populateGrid() {
        for (number in numbers) {
            val textview = TextView(this).apply {
                text = number.toString()
                textSize = 16f
                setPadding(16, 16, 16, 16)
                setBackgroundColor(Color.LTGRAY)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
            binding.gridLayout.addView(textview)
        }
    }

    private fun spinnerSetup() {
        val rules = listOf("Odd Numbers", "Even Numbers", "Prime Numbers", "Fibonacci Numbers")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, rules)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedRule = rules[position]
                highlightingNumbers(selectedRule)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }


    private fun highlightingNumbers(rule: String) {

        for (i in 0 until binding.gridLayout.childCount) {
            val textView = binding.gridLayout.getChildAt(i) as TextView
            val number = textView.text.toString().toInt()

            when (rule) {
                "Odd Numbers" -> if (number % 2 != 0) textView.setBackgroundColor(Color.YELLOW) else
                    textView.setBackgroundColor(Color.LTGRAY)

                "Even Numbers" -> if (number % 2 == 0) textView.setBackgroundColor(Color.YELLOW) else
                    textView.setBackgroundColor(Color.LTGRAY)

                "Prime Numbers" -> if (isPrime(number)) textView.setBackgroundColor(Color.YELLOW) else
                    textView.setBackgroundColor(Color.LTGRAY)

                "Fibonacci Numbers" -> if (isFibonacci(number)) textView.setBackgroundColor(Color.YELLOW) else
                    textView.setBackgroundColor(Color.LTGRAY)


            }

        }
    }

    private fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        for (i in 2..sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) return false
        }
        return true
    }


    private fun isFibonacci(num: Int): Boolean {
        val isPerfectSquare = { x: Int ->
            val s = sqrt(x.toDouble()).toInt(); s * s == x
        }
        return isPerfectSquare(5 * num * num + 4) || isPerfectSquare(5 * num * num - 4)
    }

}