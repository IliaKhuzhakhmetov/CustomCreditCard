package com.example.creditcardlibrary.view

fun String.toMask(mask: String): String {
    val arr = ArrayList<Char>()
    arr.addAll(
        this.replace(" ", "")
            .toCharArray()
            .toTypedArray()
    )
    for (i in 0 until mask.length)
        if (mask[i] == ' ' &&
            i < arr.size &&
            arr[i] != ' '
        ) arr.add(i, ' ')

    return arr.toString()
        .replace("[", "")
        .replace("]", "")
        .replace(", ", "")
}

fun String.isNumber(): Boolean {
    if ( this.isEmpty()) return false
    for (i in 0 until length)
        if (!Character.isDigit(this[i]) && this[i] != ' ') return false

    return true
}

fun String.removeAll(elements: String) = this
    .toCharArray()
    .filter { !elements.contains(it.toString()) }
    .joinToString(separator = "", transform = { it.toString() })

fun String.removeAll(vararg elements: String) = this
    .toCharArray()
    .filter { !elements.contains(it.toString()) }
    .joinToString(separator = "", transform = { it.toString() })