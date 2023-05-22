package com.taxi.apnisawari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taxi.apnisawari.databinding.FragmentRegisterBinding
import com.taxi.apnisawari.databinding.FragmentRegisterDriverBinding


class RegisterDriver : Fragment() {

    private lateinit var binding: FragmentRegisterDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterDriverBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = requireActivity()
        binding.executePendingBindings()
        binding.drivername.requestFocus()
        binding.driverphone.requestFocus()
        binding.drivertype.requestFocus()
        return binding.root
    }
}
