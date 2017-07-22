package com.ekeitho.reduxy

import android.databinding.ObservableField
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

    fun onNameChanged(s : CharSequence, start : Int, before : Int, count : Int) {
        eventStream.onNext(NameChangeEvent(s.toString()))
    }

    fun onAgeChanged(s : CharSequence, start : Int, before : Int, count : Int) {
        // If parsing this CharSequence to Integer fails, it means that
        // the edittext has not been set to number input, and therefore
        // we should crash
        eventStream.onNext(AgeChangeEvent(Integer.parseInt(s.toString())))
    }

    fun onButtonClicked(v : View?) {
        eventStream.onNext(ButtonClickEvent())
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