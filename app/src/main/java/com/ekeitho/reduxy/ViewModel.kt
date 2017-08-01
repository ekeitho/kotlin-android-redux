package com.ekeitho.reduxy

import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.Subject

object ViewModel {

    val nameObs = ObservableField<String>()
    val ageObs = ObservableField<Int>()
    val isHappy = ObservableField<Boolean>()
    lateinit var eventStream : Subject<Event>

    var subscription : Disposable = Disposables.disposed()

    var buttonClickListener = object : View.OnClickListener {
        override fun onClick(p0: View?) {
            eventStream.onNext(ButtonClickEvent())
        }
    }

    var nameWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            eventStream.onNext(NameChangeEvent(s.toString()))
        }
    }

    var ageWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {}
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            eventStream.onNext(AgeChangeEvent(Integer.parseInt(s.toString())))
        }
    }

    fun init(eventStream : Subject<Event>, stateStream: Observable<ApplicationState>) {
        // this is more for idempotency
        // so if someone were to make a mistake of calling init more than they should
        // and we are dealing with 3 threads, then they would get back the same ref to the subscription
        if (subscription.isDisposed) {
            this.eventStream = eventStream
            subscription = stateStream.subscribe({ state: ApplicationState ->
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
    }
}