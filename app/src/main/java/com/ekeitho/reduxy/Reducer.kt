package com.ekeitho.reduxy

import io.reactivex.Observable
import io.reactivex.subjects.Subject

class Reducer(initialState : Store, events : Observable<Event>, store : Subject<Store>) {

    private var keithStore : Store

    init {
        keithStore = initialState
        // send initial update
        store.onNext(keithStore)
        subscribeToEvents(events, store)
    }

    companion object {
        var instance : Reducer? = null

        fun getInstance(initialState : Store, events : Observable<Event>, store : Subject<Store>) : Reducer {
            if (instance == null) instance = Reducer(initialState, events, store)
            return instance!!
        }
    }

    fun subscribeToEvents(events: Observable<Event>, store: Subject<Store>) {
        events.subscribe({ event: Event ->
            val temp = keithStore

            if (event is ButtonClickEvent) {
                temp.name += "!"
                temp.age++
                keithStore =  Store(temp.name, temp.age, !temp.isHappy)
            } else {
                throw UnsupportedOperationException("Reducer received unrecognized event of type ${event::class}")
            }
            store.onNext(keithStore)
        })
    }

}
