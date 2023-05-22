package com.taxi.apnisawari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.taxi.apnisawari.databinding.FragmentOtpBinding
import com.taxi.apnisawari.databinding.FragmentRegisterBinding


class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOtpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.executePendingBindings()

        binding.btnOtp.setOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_mainScreen)
        }
        return binding.root
    }

}