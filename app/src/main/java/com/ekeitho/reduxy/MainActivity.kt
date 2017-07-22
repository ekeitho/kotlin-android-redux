package com.ekeitho.reduxy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ekeitho.reduxy.databinding.ActivityMainBinding
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/*
    Android application is made up of its

        * Application State
            - This represents every state an activity or fragment can have.
            - However, keep in mind to make this as stateless as possible (basically don't use many variables)
                - Making it stateless as possible leads to:
                    * easier to maintain and test, smaller chance of race conditions and app side defects

            * singleton - since only need to initialize once for the life of the application

        * Application Event Stream
            - events are emitted by an activity, fragment, views, ..
            - basically anything that has access to a context

            * singleton - since the event stream should never change during the apps life

        * Activity/Fragments
            - the activity consumes user inputs and other intents,
            - which these are passed to the event stream and this action purely updates
            - the application state in some way
 */


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        if (savedInstanceState == null) {

            // View Model and Reducer are singletons (kotlin uses objects to accomplish this sugaring)
                // therefore since the references never change...
                // streams are kept in sync, with up-to-date data, and rotation works out of the box

            // view models listen to updates from the store
            // helpful things to pass here are customized callbacks

            // subjects are observers and observables
            // therefore turning it just to an observable - means that VM CAN'T MAKE ANY CHANGES TO THE STATE!!!

            val stateStream = BehaviorSubject.createDefault(ApplicationState("Keith", 25, true))
            val eventStream : Subject<Event> = PublishSubject.create()

            ViewModel.init(eventStream, stateStream.hide())

            // reducers listen to events sent by the system or user.
            // when events are received, reducer sends update to the store - which VM is subscribed to
            Reducer.init(eventStream.hide(), stateStream)

            // Reducer and View Models should only know about the observables and not the concrete details of the stream
        }
        binding.viewModel = ViewModel
    }
}



