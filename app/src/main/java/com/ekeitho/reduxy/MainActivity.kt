package com.ekeitho.reduxy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.ekeitho.reduxy.databinding.ActivityMainBinding
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject


class MainActivity : AppCompatActivity(), KeithButtonClick  {

    lateinit var eventSubject : PublishSubject<KeithEvent>
    lateinit var storeSubject : BehaviorSubject<KeithStore>
    lateinit var keithVM : KeithViewModel
    lateinit var keithReducer : KeithReducer
    private val save_name = "SAVE_NAME"
    private val save_age = "SAVE_AGE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        var keithStore : KeithStore

        if (savedInstanceState == null) {
            keithStore = KeithStore("keith", 25, true)
        } else {
            val name = savedInstanceState.getString(save_name)
            val age  = savedInstanceState.getInt(save_age)
            keithStore = KeithStore(name, age, true)
        }

        eventSubject = PublishSubject.create()
        storeSubject = BehaviorSubject.create()

        keithVM = KeithViewModel(storeSubject, this)
        keithReducer = KeithReducer(keithStore, eventSubject, storeSubject)
        binding.viewModel = keithVM
    }

    override fun onClick(v: View?) {
        eventSubject.onNext(KeithEvent("button", 0))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(save_name, storeSubject.value.name)
        outState.putInt(save_age, storeSubject.value.age)
        super.onSaveInstanceState(outState)
    }

}

