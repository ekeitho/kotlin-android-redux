package com.ekeitho.reduxy

import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

object Reducer {

    var subscription : Disposable = Disposables.disposed()

    // Reducers do not state state, and they do NOT mutate state.
    fun init(eventSubject : PublishSubject<Event>, stateSubject: BehaviorSubject<ApplicationState>) {
        var state = stateSubject.value

        // this is for idempotency
        // so if someone were to make a mistake of calling init more than they should
        // and we are dealing with 3 threads, then they would get back the same ref to the subscription
        if (subscription.isDisposed) {
            subscription = eventSubject.subscribe({ event: Event ->
                val temp = state

                if (event.type == "name") {
                    //
                } else if (event.type == "desc") {
                    //
                } else if (event.type == "button") {
                    temp.name += "!"
                    temp.age++
                    state = ApplicationState(temp.name, temp.age, !temp.isHappy)
                } else {
                    throw UnsupportedOperationException()
                }

                stateSubject.onNext(state)
            })
        }
    }

}
