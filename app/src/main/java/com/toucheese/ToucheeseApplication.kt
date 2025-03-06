package com.toucheese

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.toucheese.app.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ToucheeseApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(
            context = this,
            appKey = BuildConfig.KAKAO_NATIVE_KEY,
        )

    }

}