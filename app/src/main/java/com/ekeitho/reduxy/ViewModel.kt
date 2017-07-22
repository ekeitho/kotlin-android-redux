package com.ekeitho.reduxy

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.subjects.Subject


class ViewModel private constructor(val eventSubject : Subject<Event>, storeObservable: Observable<Store>) {

    private val nameObs = ObservableField<String>()
    private val ageObs = ObservableField<Int>()
    private val isHappy = ObservableField<Boolean>()

    init {
        storeObservable.subscribe({ store: Store ->
            if (nameObs.get() != store.name) {
                nameObs.set(store.name)
            }
            if (ageObs.get() != store.age) {
                ageObs.set(store.age)
            }
            if (isHappy.get() != store.isHappy) {
                isHappy.set(store.isHappy)
            }
        })
    }

    companion object {
        private var instance : ViewModel? = null

        fun getInstance(eventSubject : Subject<Event>, storeObservable: Observable<Store>) : ViewModel {
            if (instance == null) instance = ViewModel(eventSubject, storeObservable)
            return instance!!
        }
    }

    fun handleClick() {
        eventSubject.onNext(ButtonClickEvent(true))
    }
}