package com.ekeitho.reduxy

import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import io.reactivex.Observable
import io.reactivex.subjects.Subject

class ViewModel(val eventSub: Subject<Event>, val stateObs: Observable<ApplicationState>) {

    val nameObs = ObservableField<String>()
    val ageObs = ObservableField<Int>()
    val isHappy = ObservableField<Boolean>()

    init {
        stateObs.subscribe({ state: ApplicationState ->
            if (nameObs.get() != state.name) {
                nameObs.set(state.name)
            }
            if (ageObs.get() != state.age) {
                ageObs.set(state.age)
            }
            if (isHappy.get() != state.isHappy) {
                isHappy.set(state.isHappy)
            }
        })
    }

    var buttonClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            eventSub.onNext(ButtonClickEvent())
        }
    }

    var nameWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            eventSub.onNext(NameChangeEvent(s.toString()))
        }
    }

    var ageWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            eventSub.onNext(AgeChangeEvent(Integer.parseInt(s.toString())))
        }
    }
}