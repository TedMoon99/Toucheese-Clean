package com.tedmoon99.data.repository.member.sign_in

import com.tedmoon99.data.model.remote.member.sign_in.SignInRequest
import com.tedmoon99.data.model.remote.member.sign_in.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {

    @POST("v1/members")
    suspend fun requestSignIn(
        @Body request: SignInRequest
    ): Response<SignInResponse>
}