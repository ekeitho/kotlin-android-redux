package com.ekeitho.reduxy

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText


@BindingAdapter("kotdux:addTextChangedListener")
fun addTextWatcher(et : EditText, tw : TextWatcher) {
    et.addTextChangedListener(tw)
}

@BindingAdapter("kotdux:addClickListener")
fun addClickListener(v : View, cl : View.OnClickListener) {
    v.setOnClickListener(cl)
}
