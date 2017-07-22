package com.ekeitho.reduxy

import io.reactivex.subjects.BehaviorSubject

object ApplicationStateStream {
    val observableState : BehaviorSubject<ApplicationState> = BehaviorSubject.create()

    init {
        val initialState = ApplicationState("Keith", 25, true)
        observableState.onNext(initialState)
    }
}