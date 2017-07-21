package com.ekeitho.reduxy

import android.databinding.ObservableField
import io.reactivex.subjects.BehaviorSubject


class ViewModel {

    val nameObs = ObservableField<String>()
    val ageObs = ObservableField<Int>()
    val isHappy = ObservableField<Boolean>()


    constructor(store : BehaviorSubject<Store>) {

        store.subscribe({ store: Store ->
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
}