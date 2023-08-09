package com.example.udemy_coroutine_prac.sample

import com.example.udemy_coroutine_prac.repository.stock.StockRepository
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//2023-06-29 15:06:10.835 9766-9766/com.example.udemy_coroutine_prac D/UserDataManager: @@@@@ start getTotalUserCount
//2023-06-29 15:06:10.838 9766-9870/com.example.udemy_coroutine_prac D/UserDataManager$getTotalUserCount: @@@@@ start [C] oroutineScope
//2023-06-29 15:06:10.838 9766-9766/com.example.udemy_coroutine_prac D/UserDataManager$getTotalUserCount: @@@@@ start coroutineScope
//2023-06-29 15:06:10.843 9766-9766/com.example.udemy_coroutine_prac D/UserDataManager$getTotalUserCount: @@@@@ end coroutineScope
//2023-06-29 15:06:11.843 9766-9872/com.example.udemy_coroutine_prac D/UserDataManager$getTotalUserCount: @@@@@ end [C] oroutineScope
//2023-06-29 15:06:11.845 9766-9870/com.example.udemy_coroutine_prac D/UserDataManager$getTotalUserCount: @@@@@ launch
//2023-06-29 15:06:12.851 9766-9766/com.example.udemy_coroutine_prac D/UserDataManager$getTotalUserCount: @@@@@ async
//2023-06-29 15:06:12.855 9766-9766/com.example.udemy_coroutine_prac D/UserDataManager: @@@@@ end getTotalUserCount

/**
 * Created by K.Kobayashi on 2023/06/29.
 */
class UserDataManager @Inject constructor(
    private val repo: StockRepository
) {
    private var count = 0
    private lateinit var resultDeferrd: Deferred<Int>

    suspend fun getTotalUserCount(): Int {

        Timber.d("@@@@@ start getTotalUserCount")

        // TODO: ★CoroutineScope() と coroutineScope() の違い
        // 　後者が新しいコルーチンを作成せずに新しいスコープを作成することです。
        // 　子コルーチンは親コルーチンスコープを使用します。
        // 　これにより、親コルーチンが実行を完了する前に子コルーチンが完了することが保証されます
        // TODO: ★coroutineScope()のメリット
        // 　　　子コルーチンが完了するのを待ってくれる
        // 　＋　例外を簡単かつ効果的に処理できる
        // 　+ 　全・個別キャンセルもできる。
//        CoroutineScope(Dispatchers.IO).launch {
//            Timber.d("@@@@@ start [C] oroutineScope")
//            val stock2 = async { repo.getStock2() }
//            delay(1000)
//            count = stock2.await()
//            Timber.d("@@@@@ $count end [C] oroutineScope")
//        }

        coroutineScope {
            Timber.d("@@@@@ start coroutineScope")
            launch(Dispatchers.IO) {
                delay(1000)
                count += 50
                Timber.d("@@@@@ launch")
            }
            resultDeferrd = async {
                delay(2000)
                Timber.d("@@@@@ async")
                70
            }
            Timber.d("@@@@@ end coroutineScope")
        }
        Timber.d("@@@@@ end getTotalUserCount")
        return count + resultDeferrd.await()
    }
}
