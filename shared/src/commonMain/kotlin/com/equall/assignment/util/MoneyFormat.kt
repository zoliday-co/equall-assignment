package com.equall.assignment.util

fun Long.toInr(withSymbol: Boolean = true): String {
    val digits = this.toString()
    val grouped = if (digits.length <= 3) {
        digits
    } else {
        val last3 = digits.substring(digits.length - 3)
        val rest = digits.substring(0, digits.length - 3)
        val sb = StringBuilder()
        var i = rest.length
        while (i > 0) {
            val start = maxOf(0, i - 2)
            if (sb.isNotEmpty()) sb.insert(0, ",")
            sb.insert(0, rest.substring(start, i))
            i = start
        }
        "$sb,$last3"
    }
    return if (withSymbol) "₹$grouped" else grouped
}
