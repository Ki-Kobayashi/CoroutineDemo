package com.example.udemy_coroutine_prac.pages

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.udemy_coroutine_prac.R
import com.example.udemy_coroutine_prac.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by K.Kobayashi on 2023/06/23.
 */
@AndroidEntryPoint
class MainFragment: Fragment(R.layout.fragment_main) {
    // TODO: 「FragmentMainBinding」：レイアウトファイルの Root を 「<layout>」で囲い、MakeProjectしないと生成されてない様子
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding get() = _binding!!

    private val vm: MainViewModel by viewModels()

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_main, container, false)
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DataBindingUtil.bind(view)

        initViews()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews() {
        // TODO: ★LiveDataを使用するには、lifecycleOwnerへの関連付けは必須
        //          （これを設定しないと、LiveData型の値は、Viewに反映されない）
        binding.lifecycleOwner = viewLifecycleOwner
        binding.myViewModel = this.vm

        // TODO:DataBindingを使用しない場合は、LiveDataに対して、Observe（データ監視）が必要
        setObserve()

        binding.inputText.addTextChangedListener {
            vm.inputText.value = it.toString()
        }

        // TODO: <<<<<<<<<<
        binding.btnDownload.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                downloadUserData()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun downloadUserData() {
        for (i in 0..200000) {
            withContext(Dispatchers.Main) {
                binding.textViewUserMassge.text = "@@@@@@ $i in ${Thread.currentThread().name}"
            }
        }
    }

    private fun setObserve() {
        // カウントを監視し、変化があれば、TextViewの中身も変更する
        vm.count.observe(viewLifecycleOwner) {
            binding.tvCount.text = it.toString()
        }
        vm.inputText.observe(viewLifecycleOwner) {
            binding.tvResult.text = it
        }
    }



}
