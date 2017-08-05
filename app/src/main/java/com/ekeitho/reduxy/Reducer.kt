package com.ekeitho.reduxy

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class Reducer(val eventObs: Observable<Event>, val stateSub: BehaviorSubject<ApplicationState>) {

    val eventLog = LinkedList<Event>()

    init {
        eventObs.subscribe({ event: Event ->
            var state = stateSub.value

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
            stateSub.onNext(state)
        })
    }
}
