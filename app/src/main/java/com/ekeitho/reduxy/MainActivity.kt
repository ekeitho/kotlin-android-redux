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

    private companion object {
        val vm: ViewModel
        val reducer: Reducer

        init {
            var stateStream = BehaviorSubject.createDefault(ApplicationState("Keith", 25, true))
            var eventStream : Subject<Event> = PublishSubject.create()
            vm = ViewModel(eventStream, stateStream.hide())
            reducer = Reducer(eventStream.hide(), stateStream)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.viewModel = vm
    }
}



