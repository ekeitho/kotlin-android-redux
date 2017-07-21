package com.ekeitho.reduxy

import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class KeithReducer {

    // Reducers do not store state, and they do NOT mutate state.
    constructor(initialState : KeithStore, events : PublishSubject<KeithEvent>, store : BehaviorSubject<KeithStore>) {
        var keithStore = initialState;
        // send initial update
        store.onNext(keithStore)

        events.subscribe({keithEvent: KeithEvent ->
            val temp = keithStore

            if (keithEvent.type == "name") {
                //
            } else if (keithEvent.type == "desc") {
                //
            } else if (keithEvent.type == "button") {
               temp.name+="!"
               temp.age++
               keithStore =  KeithStore(temp.name, temp.age, !temp.isHappy)
            } else {
                throw UnsupportedOperationException()
            }

            store.onNext(keithStore)
        })
    }

}
