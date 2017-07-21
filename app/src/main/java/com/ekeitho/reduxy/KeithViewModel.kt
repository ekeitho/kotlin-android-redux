package com.ekeitho.reduxy

import android.databinding.ObservableField
import io.reactivex.subjects.PublishSubject


class KeithViewModel {

    val nameObs = ObservableField<String>()
    val ageObs = ObservableField<Int>()
    val isHappy = ObservableField<Boolean>()
    var buttonClick : KeithButtonClick


    constructor(store : PublishSubject<KeithStore>, buttonClick: KeithButtonClick) {
        this.buttonClick = buttonClick

        store.subscribe({ keithStore: KeithStore ->
            if (nameObs.get() != keithStore.name) {
                nameObs.set(keithStore.name)
            }
            if (ageObs.get() != keithStore.age) {
                ageObs.set(keithStore.age)
            }
            if (isHappy.get() != keithStore.isHappy) {
                isHappy.set(keithStore.isHappy)
            }
        })

    }
}