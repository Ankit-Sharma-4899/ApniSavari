package com.taxi.apnisawari

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.taxi.apnisawari.databinding.FragmentRegisterBinding
import com.taxi.apnisawari.databinding.FragmentSplashBinding
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class RegisterFragment : Fragment() {
val TAG="TESTINGMOBILE"
private lateinit var binding: FragmentRegisterBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var countDownTimer: CountDownTimer
    lateinit var storedVerificationId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.registerasdriver.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_registerDriver)
        }
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = requireActivity()
        binding.executePendingBindings()
        binding.phonenumber.requestFocus()
        auth = FirebaseAuth.getInstance()
        binding.btngetotp.setOnClickListener {
            AppUtils.showProgressBar(requireContext(),"Please wait", "Generating otp..")
               var number = binding.phonenumber.text?.trim().toString()
                if (number.isNotEmpty()){
                    if (number.length == 10){
                        sendVerificationCode("+91" + binding.phonenumber.text.toString())

                    }else{
                        Toast.makeText(requireContext() , "Please Enter correct Number" , Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(requireContext() , "Please Enter Number" , Toast.LENGTH_SHORT).show()

                }

            }
        binding.btnsubmitotp.setOnClickListener {
            val combineOtp: String =
                binding.edt1.text.toString() + binding.edt2.text.toString() + binding.edt3.text.toString() + binding.edt4.text.toString() + binding.edt5.text.toString() + binding.edt6.text.toString()
            if (combineOtp.isNotEmpty())
                verifyCode(combineOtp)
        }
        attachTextWatchersSMS()
        binding.txtResendOtp.setOnClickListener {
            binding.edt1.text = null
            binding.edt2.text = null
            binding.edt3.text = null
            binding.edt4.text = null
            binding.edt5.text = null
            binding.edt6.text = null
            AppUtils.showProgressBar(requireContext(),"Please wait", "Generating otp..")
            sendVerificationCode("+91" + binding.phonenumber.text.toString())

        }

        return binding.root
    }
    fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }
    fun otpCountDown(time: Long) {

        if (::countDownTimer.isInitialized) {
            countDownTimer.cancel()
        }
        countDownTimer = object : CountDownTimer(time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f = DecimalFormat("00")
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.txtTimer.text =
                    "00 : ${f.format(sec)}"
                binding.txtResendOtp.isEnabled = false
                binding.txtResendOtp.visibility = View.INVISIBLE
                binding.txtResendOtpTitle.visibility = View.INVISIBLE

                /*resendTimer.text = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60)*/
            }

            override fun onFinish() {
                //resendTimer.text = "Resend OTP?"

                //resendTimer.setTextColor(resources.getColor(R.color.purple_700))
                binding.txtResendOtp.visibility = View.VISIBLE
                binding.txtResendOtp.isEnabled = true
                binding.txtResendOtpTitle.visibility = View.VISIBLE
                binding.txtTimer.visibility = View.INVISIBLE

            }
        }.start()
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(requireContext() , "Authenticate Successfully" , Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_mainScreen)
                    AppUtils.hideprogressbar()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d(TAG, "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
            .addOnFailureListener {
                AppUtils.hideprogressbar()
                Toast.makeText(requireContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            val code: String? = credential.smsCode;

            // checking if the code
            // is null or not.
            if (code != null && code.length == 6) {

                // if the code is not null then
                // we are setting that code to
                // our OTP edittext field.
                val edit1 = Editable.Factory.getInstance().newEditable(code[0].toString())
                val edit2 = Editable.Factory.getInstance().newEditable(code[1].toString())

                val edit3 = Editable.Factory.getInstance().newEditable(code[2].toString())

                val edit4 = Editable.Factory.getInstance().newEditable(code[3].toString())

                val edit5 = Editable.Factory.getInstance().newEditable(code[4].toString())

                val edit6 = Editable.Factory.getInstance().newEditable(code[5].toString())
                binding.edt1.text = edit1
                binding.edt2.text = edit2
                binding.edt3.text = edit3
                binding.edt4.text = edit4
                binding.edt5.text = edit5
                binding.edt6.text = edit6
                verifyCode(code);
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            AppUtils.hideprogressbar()
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d(TAG, "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d(TAG, "onVerificationFailed: ${e.toString()}")
            }
            Toast.makeText(requireContext(), "${e.toString()}", Toast.LENGTH_SHORT).show()

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            AppUtils.hideprogressbar()
            binding.mobileNumberScreen.visibility=View.GONE
            binding.constraintotp.visibility=View.VISIBLE
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            storedVerificationId = verificationId
            binding.txtResendOtp.isEnabled = false
            binding.txtResendOtp.visibility = View.INVISIBLE
            binding.txtResendOtpTitle.visibility = View.INVISIBLE
            binding.constraintotp.visibility = View.VISIBLE

            binding.txtTimer.visibility = View.VISIBLE

            otpCountDown(25000)
        }
    }
    private fun verifyCode(code: String) {
        AppUtils.showProgressBar(requireContext(),"Please wait", "Validating Otp..")
        // below line is used for getting
        // credentials from our verification id and code.
        val credential = PhoneAuthProvider.getCredential(storedVerificationId, code)

        // after getting credential we are
        // calling sign in method.
        signInWithPhoneAuthCredential(credential)
    }
    private fun attachTextWatchersSMS() {
        binding.edt1.addTextChangedListener(GenericTextWatcher(binding.edt1, binding.edt2))
        binding.edt2.addTextChangedListener(GenericTextWatcher(binding.edt2, binding.edt3))
        binding.edt3.addTextChangedListener(GenericTextWatcher(binding.edt3, binding.edt4))
        binding.edt4.addTextChangedListener(GenericTextWatcher(binding.edt4, binding.edt5))
        binding.edt5.addTextChangedListener(GenericTextWatcher(binding.edt5, binding.edt6))
        binding.edt2.setOnKeyListener(
            GenericTextWatcher.GenericKeyEvent(
                binding.edt2,
                binding.edt1
            )
        )

        binding.edt3.setOnKeyListener(
            GenericTextWatcher.GenericKeyEvent(
                binding.edt3,
                binding.edt2
            )
        )

        binding.edt4.setOnKeyListener(
            GenericTextWatcher.GenericKeyEvent(
                binding.edt4,
                binding.edt3
            )
        )

        binding.edt5.setOnKeyListener(
            GenericTextWatcher.GenericKeyEvent(
                binding.edt5,
                binding.edt4
            )
        )

        binding.edt6.setOnKeyListener(
            GenericTextWatcher.GenericKeyEvent(
                binding.edt6,
                binding.edt5
            )
        )

        binding.edt2.setOnKeyListener(
            GenericTextWatcher.GenericKeyEvent(
                binding.edt2,
                binding.edt1
            )
        )
    }
    class GenericTextWatcher(private val currentView: EditText, nextView: EditText?) :
        TextWatcher {
        private val nextView: EditText?
        override fun afterTextChanged(editable: Editable) {
            // TODO Auto-generated method stub
            val text = editable.toString()
            if (nextView != null && text.length == 1) {
                nextView.requestFocus()
            }
            if (text.length > 1) {
                currentView.setText(text[text.length - 1].toString())
                currentView.setSelection(1)
            }
        }

        override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            // TODO Auto-generated method stub
        }

        override fun onTextChanged(arg0: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
            // TODO Auto-generated method stub
        }

        init {
            this.nextView = nextView
        }

        class GenericKeyEvent(
            private val currentView: EditText,
            private val previousView: EditText?
        ) :
            View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL && currentView.text.toString()
                        .isEmpty()
                ) {
                    previousView?.requestFocus()
                    return true
                }
                return false
            }
        }
    }
}
