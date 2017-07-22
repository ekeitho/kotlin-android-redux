package com.ekeitho.reduxy


data class Store(var name : String, var age : Int, var isHappy: Boolean) {
    companion object {
        var instance : Store? = null

        fun getInstance(name: String, age: Int, isHappy: Boolean) : Store {
            if (instance == null) instance = Store(name, age, isHappy)
            return instance!!
        }
    }
}
