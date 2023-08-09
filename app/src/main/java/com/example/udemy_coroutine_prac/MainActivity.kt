package com.example.udemy_coroutine_prac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint


////  TODO:　★ .自作アニメ―ションの作成（例：遷移Animation）

//  ======================   【コルーチン：準備】  ======================
//  TODO:　★ AndroidAPPにおいて、メイン（UI）スレッドは一つのみ
//                        重たい処理をすると、メインスレッドがブロック（その処理のみだけ）され、他のUI操作ができなくなる
//                             ユーザー的には、フリーズしたように見える（ユーザビリティ悪い）。
//  TODO:　★ .Dispatchers.Main
//                      UI関数の呼び出し、一時停止の呼び出し、LiveData更新・取得など、
//                          小さくて軽量なタスクにのみ使用。
// TODO:　★ Dispatchers.IO
//                        API通信、ローカルDB操作、ファイル操作地に利用する



// TODO:　★ Mainスレッドでコルーチン開始、repositoryの”引数にDispatchers.IO”を取り、各メソッドでwithContext（ioDispatcher）で切り替える.
// TODO:　★ . viewModelScope プロパティは Dispatchers.Main にハードコード
// TODO:　★ .コルーチンスコープの種類
//                    ーGlobalScope: アプリがのプロセスが終了するまでキャンセル
// TODO:　★ .



// TODO:　★ .処理の結果が必要な場合は async を使い、そうでない場合は launch を使う。
//  . TODO:　★ launchの戻り値；JOB（ただし、呼び出し元に処理結果は返さない）　
//                    asyncの戻り値；Deferred(Future的なもの),  Defferd は await を持っている.
// TODO:　★ .await() を呼び出した時点でまだ fetchData() の処理が完了してなければ、fetchData() が完了するまで呼び出し元は中断され、
//                      fetchData() の処理が完了したら結果を受け取って後続の処理が実行
// TODO:　★ .await() は suspend 関数



//  . TODO:　★ コルーチンスコープは、コルーチンの開始・キャンセルができるやつ（スコープ外では非同期のためキャッチできない）.
//  . TODO:　★ コルーチンがキャンセルされた際には suspend 関数で CancellationException の例外がスローされるので、
//                  ※　try ~ catch を使うことでキャンセルされたことを判別
//                  ※　キャンセルすると、それ以降そのコルーチンスコープから新しいコルーチンを起動できな.
//  . TODO:　★ 親コルーチンをキャンセルすると、繋がっている全てのコルーチンスコープがキャンセル.
//  . TODO:　★ コルーチンで例外が発生した場合、親を含めた繋がっている全てのコルーチンスコープがキャンセル.

//  . TODO:　★ 個別のコルーチンをキャンセルするには、JOBを取得しキャンセルする（lanunch起動の戻り値 or Scopeの事前定義時にセットしておく）
//                  val scope = CoroutineScope(Dispatchers.IO + Job())
//                      ※Jobがセットされていないと、キャンセルができない点に注意.


//  . TODO:　★ .例外のキャッチは、xxxScope.launch{}内でやる
//                      コルーチンのキャンセルを有効にするには、CancellationException 型の例外を使用しない。
//  . TODO:　★               .CancellationExceptionは、キャッチしない or 再Throwする
//  . TODO:　★ .
//  . TODO:　★ .
//  . TODO:　★ .
//  . TODO:　★ .
//  . TODO:　★ .
//  . TODO:　★ .
//  . TODO:　★ .

//  . TODO:　★ .
//  . TODO:　★ .

//  ======================   【コルーチン：準備】  ======================
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .

//  ======================   【DataBinding：準備】  ======================
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .
//  TODO:　★ .
//
//
//
// .
//
//
//
//
//  .




//  ======================   【DataBinding：使用】  ======================
//  TODO:　★ .
//
//
//
//
//
//
//
//   .



//  ======================   【DataBinding：LiveDataとの利用方法】  ======================
//  TODO:　★ .
//
//
//
//
//
//
//
//   .




@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
