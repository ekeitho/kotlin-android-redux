package com.ekeitho.reduxy

import io.reactivex.subjects.PublishSubject

class KeithReducer {

    var keithStore = KeithStore("keith", 10, true)

    constructor(events : PublishSubject<KeithEvent>, store : PublishSubject<KeithStore>) {
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
