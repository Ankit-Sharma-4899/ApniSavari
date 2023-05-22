package com.taxi.apnisawari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }


}