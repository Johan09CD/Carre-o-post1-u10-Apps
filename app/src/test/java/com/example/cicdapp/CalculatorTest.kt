package com.example.cicdapp

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Pruebas unitarias de la clase Calculator.
 * Cubren todas las operaciones para garantizar > 60% de cobertura de líneas.
 */
class CalculatorTest {

    private lateinit var calculator: Calculator

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    // --- Tests de suma ---

    @Test
    fun `add returns correct sum of two positive numbers`() {
        assertEquals(5.0, calculator.add(2.0, 3.0), 0.001)
    }

    @Test
    fun `add returns correct sum with negative numbers`() {
        assertEquals(-1.0, calculator.add(2.0, -3.0), 0.001)
    }

    @Test
    fun `add returns zero when both operands are zero`() {
        assertEquals(0.0, calculator.add(0.0, 0.0), 0.001)
    }

    // --- Tests de resta ---

    @Test
    fun `subtract returns correct difference`() {
        assertEquals(2.0, calculator.subtract(5.0, 3.0), 0.001)
    }

    @Test
    fun `subtract returns negative when result is negative`() {
        assertEquals(-3.0, calculator.subtract(2.0, 5.0), 0.001)
    }

    // --- Tests de multiplicación ---

    @Test
    fun `multiply returns correct product`() {
        assertEquals(15.0, calculator.multiply(3.0, 5.0), 0.001)
    }

    @Test
    fun `multiply by zero returns zero`() {
        assertEquals(0.0, calculator.multiply(5.0, 0.0), 0.001)
    }

    @Test
    fun `multiply negative numbers returns positive`() {
        assertEquals(6.0, calculator.multiply(-2.0, -3.0), 0.001)
    }

    // --- Tests de división ---

    @Test
    fun `divide returns correct quotient`() {
        assertEquals(2.5, calculator.divide(5.0, 2.0), 0.001)
    }

    @Test(expected = ArithmeticException::class)
    fun `divide by zero throws ArithmeticException`() {
        calculator.divide(5.0, 0.0)
    }

    // --- Tests de porcentaje ---

    @Test
    fun `percentage calculates correctly`() {
        assertEquals(25.0, calculator.percentage(100.0, 25.0), 0.001)
    }

    @Test
    fun `percentage of zero returns zero`() {
        assertEquals(0.0, calculator.percentage(0.0, 50.0), 0.001)
    }

    // --- Tests de isPositive ---

    @Test
    fun `isPositive returns true for positive number`() {
        assertTrue(calculator.isPositive(5.0))
    }

    @Test
    fun `isPositive returns false for negative number`() {
        assertFalse(calculator.isPositive(-3.0))
    }

    @Test
    fun `isPositive returns false for zero`() {
        assertFalse(calculator.isPositive(0.0))
    }
}
