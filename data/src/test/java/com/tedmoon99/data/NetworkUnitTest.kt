package com.tedmoon99.data

import com.google.gson.annotations.SerializedName
import com.tedmoon99.data.di.NetworkModule
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.http.GET

class NetworkUnitTest {

    private val conceptMap = mapOf(
        Pair(1, "생동감 있는 실물 느낌"),
        Pair(2, "플래쉬/아이돌 느낌"),
        Pair(3, "흑백/블루 배우 느낌"),
        Pair(4, "내추럴 화보 느낌"),
        Pair(5, "선명하고 인형같은 느낌"),
        Pair(6, "필터/수채화 그림체 느낌"),

    )

    private lateinit var testInterface: NetworkUnitTestInterface
    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null
    private var interceptor: HttpLoggingInterceptor? = null

    @Before
    fun setRetrofit() {
        interceptor = NetworkModule.provideLoggingInterceptor()
        // OkHttp 설정
        okHttpClient = NetworkModule.provideOkHttpClient(interceptor!!)
        // Retrofit 설정
        retrofit = NetworkModule.provideRetrofit(okHttpClient!!)
        // Interface 설정
        testInterface = retrofit!!.create(NetworkUnitTestInterface::class.java)
    }

    @Test
    fun getRetrofitTest() = runBlocking {

        val conceptList = testInterface.getConcepts()
        for (i in 0 until conceptMap.size) {
           assert(conceptList[i].name == conceptMap[i+1])
        }
    }

    @After
    fun releaseRetrofit() {
        retrofit = null
        okHttpClient = null
        interceptor = null
    }

}

private interface NetworkUnitTestInterface {
    @GET("v1/concepts")
    suspend fun getConcepts(): List<Concept>

}

private data class Concept(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)