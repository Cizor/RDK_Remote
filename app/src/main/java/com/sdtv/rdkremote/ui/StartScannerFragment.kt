package com.sdtv.rdkremote.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sdtv.rdkremote.databinding.FragmentStartScannerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartScannerFragment : Fragment() {

    private lateinit var binding: FragmentStartScannerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartScannerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        binding.button.setOnClickListener {
            val action = StartScannerFragmentDirections.actionStartScannerFragmentToScannerFragment()
            findNavController().navigate(action)
        }
        super.onStart()
    }
}