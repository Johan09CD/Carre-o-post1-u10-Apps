package com.example.cicdapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad principal de la aplicación CICDApp.
 * Implementa una calculadora básica para demostrar el pipeline CI/CD.
 */
class MainActivity : AppCompatActivity() {

    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNumA = findViewById<EditText>(R.id.etNumberA)
        val etNumB = findViewById<EditText>(R.id.etNumberB)
        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnDivide = findViewById<Button>(R.id.btnDivide)

        btnAdd.setOnClickListener {
            performOperation(etNumA, etNumB, tvResult) { a, b -> calculator.add(a, b) }
        }
        btnSubtract.setOnClickListener {
            performOperation(etNumA, etNumB, tvResult) { a, b -> calculator.subtract(a, b) }
        }
        btnMultiply.setOnClickListener {
            performOperation(etNumA, etNumB, tvResult) { a, b -> calculator.multiply(a, b) }
        }
        btnDivide.setOnClickListener {
            performOperation(etNumA, etNumB, tvResult) { a, b -> calculator.divide(a, b) }
        }
    }

    private fun performOperation(
        etA: EditText, etB: EditText, tvResult: TextView,
        operation: (Double, Double) -> Double
    ) {
        val a = etA.text.toString().toDoubleOrNull() ?: 0.0
        val b = etB.text.toString().toDoubleOrNull() ?: 0.0
        try {
            val result = operation(a, b)
            tvResult.text = getString(R.string.result_format, result)
        } catch (e: ArithmeticException) {
            tvResult.text = getString(R.string.error_division)
        }
    }
}
