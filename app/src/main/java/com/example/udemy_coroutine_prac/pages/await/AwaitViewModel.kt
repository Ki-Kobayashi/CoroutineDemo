package com.example.udemy_coroutine_prac.pages.await

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.udemy_coroutine_prac.repository.stock.StockRepository
import com.example.udemy_coroutine_prac.sample.UserDataManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by K.Kobayashi on 2023/06/25.
 */







@HiltViewModel
class AwaitViewModel @Inject constructor(
    private val repo: StockRepository,
    private val manager: UserDataManager
) : ViewModel() {

    // TODO:クラス生成時に、既に取得してきてくれる
    val userCount = liveData(Dispatchers.IO) {
        val result = manager.getTotalUserCount()
        emit(result)
    }

    val stock1 = liveData(Dispatchers.IO) {
        val result = repo.getStock1()
        emit(result)
    }

    private val _allStocks = MutableLiveData<Pair<Int, Int>>()
    val allStocks: LiveData<Pair<Int, Int>> get() = _allStocks

    fun loadAllStockInfo() {
        viewModelScope.launch {
            // TODO: （並列：すべての処理が同時に行われる。（処理が遅いものに合わせ、計2秒かかる処理）
//            val jobList = listOf(
//                async { repo.getStock1() },
//                async { repo.getStock2() },
//            )
//
//            Timber.d("@@@@ start: 並列")
//            val resultList = jobList.awaitAll()

            val stock1 = async { repo.getStock1() }
            val stock2 = async { repo.getStock2() }

            Timber.d("@@@@ end: 並列")
            _allStocks.value = Pair(stock1.await(), stock2.await())


//            Timber.d("@@@@ start: 並行")
//            // TODO: （並行：すべての処理が順次行われる（処理時間の合計である、計3秒かかる処理）
//            val stock1 = repo.getStock1() // 1秒かかる処理
//            val stock2 = repo.getStock2() // 2秒かかる処理
//
//            Timber.d("@@@@ end：並行: $stock1, 2: $stock2")
//            _allStocks.postValue(Pair(stock1, stock2))
        }
    }
}
