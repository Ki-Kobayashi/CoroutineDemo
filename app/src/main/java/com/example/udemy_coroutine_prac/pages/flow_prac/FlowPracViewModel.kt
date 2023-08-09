package com.example.udemy_coroutine_prac.pages.flow_prac

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udemy_coroutine_prac.repository.stock.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


// TODO: collect: consumerが受け取れるようになったら、producerは次の値を送る（Flowのデフォはコレ）
// TODO: buffer: producerは送れる値をCunsumerの消費を待たずに送り続ける。Cunsumerは遅れながらも消費していく（Flowのデフォはコレ）
// TODO: collectLatest: producerが送る値の最新値をconsumerに送る（消費の間に合わなかった値はスルーされる）
// ーーーーー以下単独の値取得に使えるーーーーー
// TODO: single:
// TODO: first:
// TODO: last:


// TODO: ★Flow > SharedFlow > StateFlow

// TODO: Flow：　ー　flowOf, asFlow, flow, channelFlow等を使って作成
//                      　ー　コールドストリーム（誰かがCollectするまで開始しない）
//                                  ※collect（launchIn）される"度"に実行（emit）されるため、重い処理を定義してると毎回呼ばれる懸念あり
//                      　ー　コールドのため、Flowに対して、emitできない
//                                  ※flow.emit(1)はできない
//                                  ※flow{emit(1)}はOK




// TODO:        ホットストリームの使い所：状態の更新を観察、ビュー・モデルから発生したイベントの収集

// TODO:　SharedFlow（ホットストリーム）：ー　簡単に複数箇所で subscribe 可能な stream（下流の複数 subscriber 間で値を共有）
//                                                              （ collect/launchIn　の度に動くわけでなく、既に値取得済なら、
//                                                                              それを他 collect/launchIn　している箇所にも共有する）。
//                                                      　ー　MutableSharedFlow、shareIn（FlowをSharedFlowに変換するやつ）　で作成
//                                                      　ー　初期値が必須
//                      　                                ー　Flowに対してemit可能（emit: susupend関数）
//                      　                                ー　BroadcastChannel（非推奨）　を　SharedFlowで書き直せる


// TODO:　StateFlow（ホットストリーム）：　ー　状態保持のための特別なSharedFlow
//                                                        ー　DataBindingに対応してない
//                                                        ー　MutableStateFlow、stateIn（FlowをStateFlowに変換するやつ）で作成可能。
//                                                        ー　初期値が必須（collect / launchIn したタイミングで直近値が1件流れてくる）
//                                                        ー　emit不要 ( mutableStateFlow.value = xxx として値をセットする：LiveData的)
//                                                        ー　値に変更がなければ、通知しない（あくまで状態保持の仕様のため）
//                                                        ー　連続して値が変更された場合、最新値"のみ"を通知
//                      　                                ー　ConflatedBroadcastChannel（非推奨）　を　StateFlow　で書き直せる
//              ・　初期値が必要でNotNullがデフォルト【Null安全・初期値が必ず通知される（Nullだとしても）】
//                      （LiveDataの場合は、
//                              ー　javaが元のため、Nullable型しかない（ヌルポの危険がある、Null安全でない）
//                              ー　observeのタイミングではなにも起きない。値が変更されたタイミング）
//              ・　メイン／ワーカー問わず、「.value　＝　ｘｘｘ」でアクセスできる
//              ・　Fragmentでcollectするとき、「lifecycleScope.launch(スレッド指定) {}」を使うため、スレッドを柔軟に制御できる
//                      （LiveData：できない）
//              ・　StateFlow はバックグラウンドにAPPがいても、CoroutineScopeが生きてれば、通知される
//                      ー　LiveData：Lifecycleが「STARTED以降」＋「フォアグランド」でないと、通知を受け取れない。

// TODO: Flow 【終端演算子】
//              launchIn、：scope.launch { flow.collect() } のシュガーシンタックス
//                                  +　後述の中間演算子を使いたいときに使用
//      （使用例）
//           flow
//               .onEach { value -> updateUi(value) }
//               .onCompletion { cause -> updateUi(if (cause == null) "Done" else "Failed") }
//               .catch { cause -> LOG.error("Exception: $cause") }
//               .launchIn(uiScope)

// TODO: Flow 【中間演算子】
//             flow{}.flowOn(Dispatchers.IO).：スレッド指定？
//              onEach,：　各出力の合間にやりたい処理を記述
//              onCompletion、：flowが完全に収集したとき、呼び出される
//              .catch{cause -> エラー時の処理}
//              take（最初の2つの値だけ取得）
//          https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/launch-in.html


// TODO: 【　Flowの定義方法　】
//              ー flow{}
//              ー リスト/Range.asFlow()　←　リスト要素を順次は出力していくようになる
//              ー　flowOf(1, 3, 5, 1, 18)
//              ー　 channelFlow：.


// TODO: Flow 【複数Flowの合成】・・・・・・２つのFlowの出力結果を、zipで展開し、加工して使用する
//          val nums = (1..3).asFlow() // 数値 1..3
//          val strs = flowOf("one", "two", "three") // 文字列
//          nums.zip(strs) { a, b -> "$a -> $b" } // 単一の文字列に構成する
//              .collect { println(it) } /。

// TODO: 【　値を収集　】 repeatOnLifecycle：（FlowをCollectしたりCancel 時に使用）
//      引数で渡したライフサイクルステートによりFlowをCollectしたりCancelしたりしてくれる
//      特定のライフサイクル時に”一度だけ”処理を実行したいといったケースのには向いていない
//              （指定ライフサイクルになるたびに、最初から実行されるため）
//              （一度だけは、WithXxxだけど、非推奨）
//       一つのFlowを収集　→　・Flow.flowWithLifecycle() メソッドを使う

//       viewLifecycleOwner.lifecycleScope.launch {
//              exampleProvider.exampleFlow()
//                  .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
//                  .collect {
//                          // Process the value.
//                  }
//        }
//        .
//         TODO:★ライフサイクルがSTARTED状態（またはそれ以上）になるたびに、
//                                  repeatOnLifecycleは新しいコルーチンでブロックを起動.
//                                  STOPPED状態になるとそれをキャンセル
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                latestNewsViewModel.uiState.collect { uiState ->
//                    when (uiState) {
//                        is LatestNewsUiState.Success -> showFavoriteNews(uiState.news)
//                        is LatestNewsUiState.Error -> showError(uiState.exception)
//                    }
//                }
//            }
//        }.





//}・
//      launchWhenXXX系：公式非推奨
//              アップストリーム フローはバックグラウンドでアクティブのままにされ、新しいアイテムが出力される可能性やリソースが浪費。

/**
 * Created by K.Kobayashi on 2023/06/29.
 */
@HiltViewModel
class FlowPracViewModel @Inject constructor(
    private val repo: StockRepository
) : ViewModel() {
    //    private var _flowInt1: StateFlow<Int> = StateFlow(0)
    private var _flowInt1 = MutableLiveData(0)
    val flowInt1: LiveData<Int> = _flowInt1

//    private var _total = MutableLiveData(0)
//    val total: LiveData<Int> = _total
    private val _flowTotal = MutableStateFlow(0)
    val flowTotal: StateFlow<Int> get() = _flowTotal

    private val startingTotal = 0

    init {
//        _total.value = startingTotal
        _flowTotal.value = startingTotal
    }

    fun plusTotal(num: Int) {
        // TODO:「MutableLiveData = Nullable」だから、以下のような呼び出しになる
//        _total.value = _total.value?.plus(num)
        // TODO:「MutableStateFlow = NotNull」。常に初期値があるため、「？」は不要
        _flowTotal.value += num
    }

    fun minusTotal(num: Int) {
        _flowTotal.value -= num
    }

    fun firstFlowCollect() {
        viewModelScope.launch {
//            repo.fitchFlowPracInt().collectLatest{
//            repo.fitchFlowPracInt().buffer{
            repo.fitchFlowPracInt()
                .map {
                    it * 3 // TODO: Map演算子：変換
                }
                .filter {
                    it % 2 == 0
                }
                    // TODO: LaunchInで書き換え可能
                .collect {
                    delay(1001)
                    // TODO: Mainスレッドで動いている
//                Timber.d("@@@@@ :  current Theead  is ${Thread.currentThread().name}")
                    Timber.d("@@@@@ :  consumer")
                    _flowInt1.value = it
                }
        }
    }
}
