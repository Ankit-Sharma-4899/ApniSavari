package com.taxi.apnisawari

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.taxi.apnisawari.databinding.FragmentMainScreenBinding
import java.util.concurrent.TimeUnit


class MainScreen : Fragment() {

    private lateinit var binding: FragmentMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentMainScreenBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = requireActivity()
        binding.executePendingBindings()
        binding.mainscreenlayout.setOnClickListener {
            it.hidekeyboard()
        }
        return binding.root

    }
    fun View.hidekeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}