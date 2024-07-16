---
category: 개발
title: Retrofit2를 이용한 API 서버통신
description: Retrofit2를 이용한 API 서버통신에 대하여
createdAt: 2021.01.08
tags:
    - Android
    - Retrofit2
    - REST API
root: .components.layout.PostPageLayout
---

## Retrofit이란?
안드로이드에서 API서버와 통신하기 위한 방법으로 HttpUrlConnection, Volley, OkHttp, Retrofit2 등이 존재합니다.    
그중에서 Retrofit2는 구현하기 쉽고 성능과 가독성이 좋다는 장점을 가지고 있습니다.

## 사용방법은?
이번 글에서는 Retrofit2를 이용하여 자신의 Github 정보를 불러오는 방법을 정리했습니다.

### 라이브러리 추가
```kotlin
implementation 'com.squareup.retrofit2:retrofit:버전정보'
implementation 'com.squareup.retrofit2:converter-gson:버전정보'
```
retrofit 관련 라이브러리를 build.gradle 파일에 추가해줍니다.

### 인터넷 권한 추가
```xml
...
<uses-permission android:name="android.permission.INTERNET" />
...
```
인터넷을 사용할 수 있도록 유저 권한을 추가해줍니다.

### Retrofit Builder 생성
```kotlin
object RetrofitBuilder {
    var api : API

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(API::class.java)
    }
}
```
코틀린의 object 키워드를 이용하여 싱글톤으로 생성해줍니다.

_baseUrl_에는 이번 글에서 사용할 깃허브 api 주소를 넣고, _addConverterFactory_에는 GsonConverter를 추가하여 JSON 형식을 DTO Class 형식으로 자동변환해줍니다.

### DTO Class 생성
https://api.github.com/users/유저아이디

유저아이디 부분에 자신의 깃허브 아이디를 입력하여 요청하면 아래 사진과 같이 JSON 형식으로 데이터가 표시됩니다.

<img src="/images/retrofit/api_spec.png" alt="api_spec" width="50%"/>

```kotlin
data class UserInfo(
    @SerializedName("login")
    val userId: String,
    val followers: Int,
    val following: Int
)
```
많은 유저 정보 중에서 유저 아이디와 팔로우, 팔로잉 정보만 안드로이드 앱에 표시해보겠습니다.   
JSON 데이터를 받아올 UserInfo라는 DTO 클래스를 생성해줍니다.

변수의 이름을 JSON의 키 값과 이름을 맞춰 매핑해주거나 @SerializedName을 통해 매핑해줄 수 있습니다.

### API Interface 생성
``` kotlin
interface API {
    @GET("users/유저아이디") // baseUrl + "user/유저아이디"
    fun getUserInfo(): Call<UserInfo>
}
```
API 인터페이스를 생성 후 어노테이션을 이용하여 HTTP Method인 POST, GET, PUT, DELETE를 설정해줍니다.

유저아이디 부분에 자신의 Gihub 아이디를 입력해줍니다.

### 요청 보내기
```kotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RetrofitBuilder.api.getUserInfo().enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                val userInfo = response.body()
                Log.d("response", "${userInfo?.login} ${userInfo?.followers} ${userInfo?.following}")
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("error", t.message.toString())
            }
        })

    }
}
```

## 끝내며
Retrofit2는 다른 안드로이드 서버통신 라이브러리에 비해 GsonConverter와 SimpleXmlConverter를 사용하여 JSON과 XML을 쉽게 파싱할 수 있고, 인터페이스를 통해 API를 관리할 수 있다는 점이 편리하게 느껴졌습니다.
