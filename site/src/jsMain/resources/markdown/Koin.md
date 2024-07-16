---
category: 개발
title: Koin을 이용한 의존성 주입
description: Koin을 이용한 의존성 주입에 대하여
createdAt: 2021.01.04
tags:
    - Android
    - DI
    - Koin
root: .components.layout.PostPageLayout
---

## 의존성 주입(Dependency Injection)이란?
내부에서 객체를 생성하는 것이 아닌 외부에서 객체를 생성하여 의존성을 주입하는 것입니다.

## 의존성 주입을 사용하는 이유는?
- 코드의 재사용성 향상 및 간결화에 도움을 줍니다.
- 객체의 생성과 사용을 분리시키고 종속된 코드를 줄여줍니다.
- 코드의 유연성과 확장성이 높아집니다.

## 의존성 주입 사용방법은?
안드로이드에서 의존성 주입을 도와주는 대표적인 라이브러리로 Dagger2, Koin, Hilt 등이 있습니다.    
이번 글에서는 **Koin**을 통해 의존성 주입을 구현해보겠습니다. 특히 Koin은 학습하기 쉽고, Kotlin에 최적화된 라이브러리입니다.

### 라이브러 추가
```kotlin
implementation "org.koin:koin-androidx-scope:버전정보"
implementation "org.koin:koin-androidx-viewmodel:버전정보"
implementation "org.koin:koin-androidx-ext:버전정보"
```
먼저 gradle에 라이브러리를 추가해줍니다.

### 예제 코드 입력
```kotlin
interface NameRepository {

    fun getName() : String
}

class NameRepositoryImpl : NameRepository {

    override fun getName(): String = "My name is Koin"
}

class GetNameUseCase(private val nameRepository: NameRepository) {

    fun getName() = nameRepository.getName()
}

class MainActivity : AppCompatActivity() {

    private val getNameUseCase: GetNameUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text.text = getNameUseCase.getName()
    }
}
```
_NameRepository_ 인터페이스와 이것을 구현하는 _NameRepositoryImpl_를 만들어줍니다.   
그리고 인터페이스를 파라미터로 받는 _GetNameUseCase_를 만들고, Activity에 Inject하여 주입해줍니다.

### 모듈 추가
```kotlin
val module = module {
    single<NameRepository> { NameRepositoryImpl() }
    single { GetNameUseCase(get()) }.bind(NameRepositoryImpl::class)
}
```
koin 모듈을 이용하여 앞에서 만든 클래스들을 koin dsl 키워드를 통해 생성해줍니다.

- single : 한 번 생성하면 앱이 살아있는 동안 계속 사용 가능한 객체를 생성
- factory : Inject 할 때마다 인스턴스 생성
- viewModel : viewModel 인스턴스를 생성
- bind : 지정된 컴포넌트의 타입을 추가적으로 바인딩
- get : 생성된 객체 중에 알맞은 객체를 주입
- applicationContext : Context를 주입

### 모듈 선언
```kotlin
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(module)
        }
    }
}

 <application
        android:name=".Application"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        ...
```
앱이 시작할 때 koin이 동작할 수 있도록 Application 클래스를 생성해주고 Manifest.xml 파일에 정의해줍니다.

## 끝내며
기존에 의존성 주입을 Dagger2로 학습하여 어렵게 느껴졌지만, Koin을 통해 다시 학습하니 코드를 관리하기 쉽고 유용하다고 느껴졌습니다.  
Dagger2가 어렵게 느껴진다면 Koin을 먼저 학습하는 것도 도움이 될 것 같습니다.
