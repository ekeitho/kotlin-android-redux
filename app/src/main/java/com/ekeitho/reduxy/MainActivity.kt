package com.ekeitho.reduxy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ekeitho.reduxy.databinding.ActivityMainBinding
import io.reactivex.subjects.PublishSubject


class MainActivity : AppCompatActivity(), KeithButtonClick  {

    lateinit var eventSubject : PublishSubject<KeithEvent>
    lateinit var storeSubject : PublishSubject<KeithStore>
    lateinit var keithVM : KeithViewModel
    lateinit var keithReducer : KeithReducer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        eventSubject = PublishSubject.create()
        storeSubject = PublishSubject.create()

        keithVM = KeithViewModel(storeSubject, this)
        keithReducer = KeithReducer(eventSubject, storeSubject)
        binding.viewModel = keithVM
    }

    override fun onClick(v: View?) {
        eventSubject.onNext(KeithEvent("button", 0))
    }
}

