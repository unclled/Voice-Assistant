package com.project.voiceassistant.utils

object NumberToWords {
    private val units = arrayOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    private val teens = arrayOf("десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    private val tens = arrayOf("", "", "двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    private val hundreds = arrayOf("", "сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")

    fun convert(number: Long): String {
        if (number == 0L) return "ноль"
        return convertInternal(number)
    }

    private fun convertInternal(number: Long): String {
        return when {
            number < 10 -> units[number.toInt()]
            number < 20 -> teens[number.toInt() - 10]
            number < 100 -> tens[number.toInt() / 10] + " " + convertInternal(number % 10)
            number < 1000 -> hundreds[number.toInt() / 100] + " " + convertInternal(number % 100)
            else -> "слишком большое число"
        }.trim()
    }
}