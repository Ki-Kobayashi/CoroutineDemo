package com.example.udemy_coroutine_prac.pages.flow_prac

import android.os.Bundle
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.udemy_coroutine_prac.R
import com.example.udemy_coroutine_prac.databinding.FragmentFlowPracBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by K.Kobayashi on 2023/06/29.
 */
@AndroidEntryPoint
class FlowPracFragment : Fragment(R.layout.fragment_flow_prac) {
    private var _binding: FragmentFlowPracBinding? = null
    private val binding: FragmentFlowPracBinding get() = _binding!!

    private val vm: FlowPracViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun initViews(view: View) {
        _binding = DataBindingUtil.bind(view)

        binding.buttonPlus.setOnClickListener { calcTotal(CalcType.PLUS) }
        binding.buttonMinus.setOnClickListener { calcTotal(CalcType.MINUS) }

        // 監視対象をセット
        setObserver()

        // Flowの値を取得
        vm.firstFlowCollect()
    }


    private fun setObserver() {
        vm.flowInt1.observe(viewLifecycleOwner) {
            binding.tvFlowPrac1.text = it.toString()
        }
//        vm.total.observe(viewLifecycleOwner) {
//            binding.tvStateFlow.text = it.toString()
//        }

//        //　方法１
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.CREATED) {
//                vm.flowTotal.collect {
//                    binding.tvStateFlow.text = it.toString()
//                }
//            }
//        }

        //　方法２
        vm.flowTotal
            .onEach { // 取得後の処理
                binding.tvStateFlow.text = it.toString()
            }
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED) // Startのタイミングになったら取得を行う
            .launchIn(lifecycleScope)
    }

    private fun calcTotal(calcType: CalcType) {
        val userInput = binding.editTextNumber.text.toString()
        if (userInput.trim().isEmpty()) {
            Snackbar.make(requireView(), "未入力です", Snackbar.LENGTH_SHORT).show()
            return
        }
        if (!userInput.isDigitsOnly()) {
            Snackbar.make(requireView(), "数字を入力してください", Snackbar.LENGTH_SHORT).show()
            return
        }
        when (calcType) {
            CalcType.PLUS -> vm.plusTotal(userInput.toInt())
            CalcType.MINUS -> vm.minusTotal(userInput.toInt())
        }
        binding.editTextNumber.text.clear()
    }
}

enum class CalcType {
    PLUS,
    MINUS
}
