package com.ekeitho.reduxy

import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ReducerUnitTest {

    lateinit var eventStream : Subject<Event>
    lateinit var stateStream : Observable<ApplicationState>

    val initialState = ApplicationState("initial", 10, true)

    // It seems like this shouldn't reset the Reducer between tests.
    // I'm honestly not sure why they pass at the moment
    @Before
    fun setup() {
        eventStream = PublishSubject.create()
        val stateSetup = BehaviorSubject.createDefault(initialState)
        Reducer.init(eventStream, stateSetup)
        stateStream = stateSetup.hide()
    }

    @Test
    fun stateStream_shouldEmitDefault() {
        val testObserver = TestObserver<ApplicationState>()
        stateStream.subscribe(testObserver)
        eventStream.onNext(ButtonClickEvent())

        val onNextEvents = testObserver.events[0]

        assertEquals(initialState, onNextEvents[0])
        assertNotEquals(ApplicationState("", 0, false), onNextEvents[0])
    }

    @Test
    fun buttonClick_shouldModifyValues() {
        val incrementedState = ApplicationState("initial!", 11, false)

        val testObserver = TestObserver<ApplicationState>()
        stateStream.subscribe(testObserver)
        eventStream.onNext(ButtonClickEvent())

        val onNextEvents = testObserver.events[0]

        assertEquals(initialState, onNextEvents[0])
        assertEquals(incrementedState, onNextEvents[1])
        assertNotEquals(initialState, onNextEvents[1])
    }

}
