package com.ekeitho.reduxy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ekeitho.reduxy.databinding.ActivityMainBinding
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var eventSubject : PublishSubject<Event>
    lateinit var storeSubject : BehaviorSubject<Store>
    lateinit var viewModel: ViewModel
    lateinit var reducer: Reducer
    private val save_name = "SAVE_NAME"
    private val save_age = "SAVE_AGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        var storeData: Store

        if (savedInstanceState == null) {
            storeData = Store("keith", 25, true)
        } else {
            val name = savedInstanceState.getString(save_name)
            val age  = savedInstanceState.getInt(save_age)
            storeData = Store(name, age, true)
        }

        eventSubject = PublishSubject.create()
        storeSubject = BehaviorSubject.create()

        // view models listen to updates from the store
            // helpful things to pass here are customize callbacks
        viewModel = ViewModel(storeSubject)

        // reducers listen to events sent by the view models
            // when events are received, reducer sends update store - which VM is listening to
        reducer = Reducer(storeData, eventSubject, storeSubject)
        binding.viewModel = viewModel
    }

    override fun onClick(v: View?) {
        eventSubject.onNext(Event("button", 0))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(save_name, storeSubject.value.name)
        outState.putInt(save_age, storeSubject.value.age)
        super.onSaveInstanceState(outState)
    }

}

