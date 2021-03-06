package com.example.creditcardlibrary.view

import android.text.Editable
import android.text.TextWatcher

interface CustomTextWatcher : TextWatcher {

    override fun afterTextChanged(s: Editable) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val sb = StringBuilder(s.length)
        sb.append(s)
        textChanged(sb.toString(), start, before)
    }

    fun textChanged(text: String, start: Int, before: Int)

}