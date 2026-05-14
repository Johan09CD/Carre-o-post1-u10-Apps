package com.example.cicdapp

/**
 * Clase Calculator - Lógica de negocio de la calculadora.
 * Esta clase contiene las operaciones matemáticas básicas y es
 * el objetivo principal de las pruebas unitarias para el quality gate de cobertura.
 */
class Calculator {

    /**
     * Suma dos números.
     * @param a primer operando
     * @param b segundo operando
     * @return resultado de a + b
     */
    fun add(a: Double, b: Double): Double = a + b

    /**
     * Resta dos números.
     * @param a primer operando
     * @param b segundo operando
     * @return resultado de a - b
     */
    fun subtract(a: Double, b: Double): Double = a - b

    /**
     * Multiplica dos números.
     * @param a primer operando
     * @param b segundo operando
     * @return resultado de a * b
     */
    fun multiply(a: Double, b: Double): Double = a * b

    /**
     * Divide dos números.
     * @param a dividendo
     * @param b divisor
     * @return resultado de a / b
     * @throws ArithmeticException si el divisor es cero
     */
    fun divide(a: Double, b: Double): Double {
        if (b == 0.0) throw ArithmeticException("División por cero no permitida")
        return a / b
    }

    /**
     * Calcula el porcentaje de un número.
     * @param value el valor base
     * @param percentage el porcentaje a calcular
     * @return el resultado del porcentaje
     */
    fun percentage(value: Double, percentage: Double): Double = value * percentage / 100.0

    /**
     * Verifica si un número es positivo.
     * @param value el número a verificar
     * @return true si el número es mayor que cero
     */
    fun isPositive(value: Double): Boolean = value > 0
}
