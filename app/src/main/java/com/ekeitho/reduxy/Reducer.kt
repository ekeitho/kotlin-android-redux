package com.ekeitho.reduxy

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.subjects.BehaviorSubject
import java.util.*

object Reducer {

    var subscription : Disposable = Disposables.disposed()

    // Reducers do not state state, and they do NOT mutate state.
    fun init(eventSubject : Observable<Event>, stateSubject: BehaviorSubject<ApplicationState>) {
        var state = stateSubject.value
        val eventLog = LinkedList<Event>()

        // this is for idempotency
        // so if someone were to make a mistake of calling init more than they should
        // and we are dealing with 3 threads, then they would get back the same ref to the subscription
        if (subscription.isDisposed) {
            subscription = eventSubject.subscribe({ event: Event ->
                eventLog.push(event)
                val temp = state.copy()

                if (event is ButtonClickEvent) {
                    temp.name += "!"
                    temp.age++
                    temp.isHappy = !temp.isHappy
                } else if (event is NameChangeEvent) {
                    temp.name = event.newName
                } else if (event is AgeChangeEvent) {
                    temp.age = event.newAge
                } else {
                    throw UnsupportedOperationException("Event of unknown type passed to Reducer")
                }
                state = ApplicationState(temp.name, temp.age, temp.isHappy)
                stateSubject.onNext(state)
            })
        }
    }

}
