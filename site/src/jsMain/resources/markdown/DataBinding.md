---
category: 개발
title: 간단한 DataBinding 사용 방법
description: DataBinding은 안드로이드 Jetpack 라이브러리 중 하나인 데이터 결합 라이브러리입니다.
createdAt: 2021.01.18
tags:
    - Android
    - DataBinding
root: .components.layout.PostPageLayout
---

# DataBinding이란?
DataBinding은 안드로이드 Jetpack 라이브러리 중 하나인 데이터 결합 라이브러리입니다.   
XML과 Data를 연결하여 유지관리와 성능향상, 메모리 누수 및 NULL 포인터 예외를 방지 할 수 있습니다.

# DataBinding 사용 방법
### 빌드 환경 변경

build.gradle
```kotlin
android {
    ...
    buildFeatures {
            dataBinding = true
    }
    ...
}

dependencies {
    implementation "androidx.lifecycle:lifecycle-extensions:버전정보"
}

```
dataBinding 요소를 app 모듈 build.gradle 파일에 추가해줍니다.   
LiveData를 사용하기 위해 lifecycle 라이브러리도 추가해줍니다.

### Layout XML 변경

activity_main.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    tools:context=".MainActivity">

    <data>
        <variable
            name="activity"
            type="com.example.databinding.MainActivity" />
    </data>

    <androidx.constraintlayout.widget.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="@{activity.text}"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent" />

    </androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```

layout 태크로 감싸고, data와 variable 태그를 통해 activity라는 이름의 결합 변수를 만들어줍니다.   
그리고 TextView의 text와 결합 변수의 text 변수를 결합 표현식으로 연결해줍니다.


### Activity 변경

MainActivity.kt
```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val text = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        
        text.value = "Hello DataBinding!"
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }
}
```
activty_main.xml 파일을 layout 태크로 감싸면 ActivityMainBinding이라는 클래스가 자동으로 생성됩니다.

DataBindingUtil.setContentView()를 통해 xml과 연결해줍니다.

MutableLiveData 타입의 text 변수의 value 값이 변경될 때마다 자동으로 xml의 TextView도 변경될 것입니다.

# 끝내며
MVVM 디자인 패턴을 사용할 때 주로 사용했었는데 필요없는 View 쪽의 코드가 줄어들고, Data가 변경될 때마다 UI가 변경되어 편리했습니다.

그리고 BindingAdapter를 같이 이용하면 추가로 필요한 Binding 속성을 만들 수 있어서 코드 유지관리와 가독성 측면에서 편리했습니다.
