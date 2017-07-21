package com.ekeitho.reduxy

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class Reducer {

    // Reducers do not store state, and they do NOT mutate state.
    constructor(initialState : Store, events : PublishSubject<Event>, store : BehaviorSubject<Store>) {
        var keithStore = initialState;
        // send initial update
        store.onNext(keithStore)

        events.subscribe({ event: Event ->
            val temp = keithStore

            if (event.type == "name") {
                //
            } else if (event.type == "desc") {
                //
            } else if (event.type == "button") {
               temp.name+="!"
               temp.age++
               keithStore =  Store(temp.name, temp.age, !temp.isHappy)
            } else {
                throw UnsupportedOperationException()
            }

            store.onNext(keithStore)
        })
    }

}
