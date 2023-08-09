package com.example.udemy_coroutine_prac.pages.await

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.udemy_coroutine_prac.R
import com.example.udemy_coroutine_prac.databinding.FragmentAwaitBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * Created by K.Kobayashi on 2023/06/25.
 */
@AndroidEntryPoint
class AwaitFragment : Fragment(R.layout.fragment_await) {
    private var _binding: FragmentAwaitBinding? = null
    private val binding: FragmentAwaitBinding get() = _binding!!

    private val vm: AwaitViewModel by viewModels()

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
        binding.awaitViewModel = vm
        binding.lifecycleOwner = viewLifecycleOwner
        setButtonClickedListener()
        setObserve()
    }

    private fun setButtonClickedListener() {
        binding.buttonStock1.setOnClickListener {
            showTvStock1()
        }
        binding.buttonStock2.setOnClickListener {
            showTvStock2()
        }
        binding.buttonAllStock.setOnClickListener {
            vm.loadAllStockInfo()
        }
    }

    private fun setObserve() {
        vm.userCount.observe(viewLifecycleOwner) {
            binding.tvStock1.text = it.toString()
        }
        vm.stock1.observe(viewLifecycleOwner) {
            binding.tvStock2.text = it.toString()
        }
        vm.allStocks.observe(viewLifecycleOwner) {
            Timber.d("@@@@- 1: ${it.first}, 2: ${it.second}")
            binding.tvAllStock.text = getString(R.string.all_stock_text, it.first, it.second)
            showTvAllStock()
        }
    }

    private fun showTvStock1() {
        binding.tvStock1.visibility = View.VISIBLE
        binding.tvStock2.visibility = View.GONE
        binding.tvAllStock.visibility = View.GONE
    }

    private fun showTvStock2() {
        binding.tvStock1.visibility = View.GONE
        binding.tvStock2.visibility = View.VISIBLE
        binding.tvAllStock.visibility = View.GONE
    }

    private fun showTvAllStock() {
        binding.tvStock1.visibility = View.GONE
        binding.tvStock2.visibility = View.GONE
        binding.tvAllStock.visibility = View.VISIBLE
    }
}
