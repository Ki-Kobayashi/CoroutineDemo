package com.example.udemy_coroutine_prac.pages

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by K.Kobayashi on 2023/06/23.
 */
@HiltViewModel
//　TODO: @Inject constructor()　を入れないと、なぜかエラーになる
class MainViewModel @Inject constructor(): ViewModel() {
    private var _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    // TODO: 【DataBinding ver】 入力テキストを即Viewに反映
    var inputText = MutableLiveData<String>()
//    // TODO: 【通常 ver】 入力テキストを即Viewに反映
//    private var _inputText = MutableLiveData<String>()
//    val inputText: LiveData<String> get() = _inputText

    init {
        _count.value = 0
    }

    fun countUp() {
        _count.value = _count.value?.plus(1)
    }
}
