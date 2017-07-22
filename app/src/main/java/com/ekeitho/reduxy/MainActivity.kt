package com.ekeitho.reduxy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ekeitho.reduxy.databinding.ActivityMainBinding
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


class MainActivity : AppCompatActivity(), View.OnClickListener {

    val eventSubject : PublishSubject<Event> = PublishSubject.create()
    val eventObservable : Observable<Event> = eventSubject.hide()
    val storeSubject : BehaviorSubject<Store> = BehaviorSubject.create()
    val storeObservable : Observable<Store> = storeSubject.hide()
    val viewModel: ViewModel = ViewModel.getInstance(eventSubject, storeObservable)
    val storeData: Store = Store.getInstance("keith", 25, true)
    val reducer: Reducer = Reducer.getInstance(storeData, eventObservable, storeSubject)
    private val save_name = "SAVE_NAME"
    private val save_age = "SAVE_AGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewModel = viewModel
    }

    override fun onClick(v: View?) {
        viewModel.handleClick()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(save_name, storeSubject.value.name)
        outState.putInt(save_age, storeSubject.value.age)
        super.onSaveInstanceState(outState)
    }

}

