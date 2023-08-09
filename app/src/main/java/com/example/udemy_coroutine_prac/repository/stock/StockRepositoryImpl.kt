package com.example.udemy_coroutine_prac.repository.stock

import dagger.Module
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by K.Kobayashi on 2023/06/25.
 */
class StockRepositoryImpl @Inject constructor(
): StockRepository {

    // TODO: 本当は、コンストラクタ経由で、Ｄｉｓｐａｔｃｈｅｒを受け取るようにする
    //  （テストをしやすくするため。ハードコードだと、うまく機能しないことがあるらしい）
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun getStock1(): Int {
        return withContext(ioDispatcher) {
            delay(1000)
            55000
        }
    }

    override suspend fun getStock2(): Int {
        return withContext(ioDispatcher) {
            delay(2000)
            3300
        }
    }

    override fun fitchFlowPracInt(): Flow<Int> {
        return flow {
            for(i in 1..20) {
                // TODO: Mainスレッドで動いている
//                Timber.d("@@@@@ :  current Theead  is ${Thread.currentThread().name}")
                Timber.d("@@@@@ :  producer")
                emit(i)
                delay(1000)
            }
        }
    }
}
