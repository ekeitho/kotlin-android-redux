package com.ekeitho.reduxy



abstract class Event

class ButtonClickEvent : Event()
data class NameChangeEvent(val newName : String) : Event()
data class AgeChangeEvent(val newAge : Int) : Event()
