package com.example.flo.data.remote

import android.util.Log
import com.example.flo.LoginView
import com.example.flo.SignUpView
import com.example.flo.data.entities.User
import com.example.flo.getRetrofit
import retrofit2.Call
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }

    fun signUp(user: User) {

        val signUpService = getRetrofit().create(AuthRetrofitInterface::class.java).apply {

            signUp(user).enqueue(object : retrofit2.Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful && response.code() == 200) {
                        val signUpResponse: AuthResponse = response.body()!!

                        Log.d("SIGNUP-RESPONSE", signUpResponse.toString())

                        when (val code = signUpResponse.code) {
                            1000 -> signUpView.onSignUpSuccess()
                            2016, 2017 -> {
                                signUpView.onSignUpFailure()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    //실패처리
                }
            })
        }
    }

    fun login(user: User) {
        val loginService = getRetrofit().create(AuthRetrofitInterface::class.java)


        loginService.login(user).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful && response.code() == 200) {
                    val loginResponse: AuthResponse = response.body()!!

                    when (val code = loginResponse.code) {
                        1000 -> loginView.onLoginSuccess(code,loginResponse.result!! )
                        else -> loginView.onLoginFailure()
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                //실패처리
            }
        })
    }
}