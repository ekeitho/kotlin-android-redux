package com.ekeitho.reduxy

import io.reactivex.subjects.PublishSubject

object ApplicationEventStream {

    val observableEventStream: PublishSubject<Event> = PublishSubject.create()

}