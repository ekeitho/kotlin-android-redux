package com.ekeitho.reduxy

import android.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject


object ViewModel {

    val nameObs = ObservableField<String>()
    val ageObs = ObservableField<Int>()
    val isHappy = ObservableField<Boolean>()

    var subscription : Disposable = Disposables.disposed()

    fun init(state: Observable<ApplicationState>) {

        // this is more for idempotency
        // so if someone were to make a mistake of calling init more than they should
        // and we are dealing with 3 threads, then they would get back the same ref to the subscription
        if (subscription.isDisposed) {
            subscription = state.subscribe({ state: ApplicationState ->
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