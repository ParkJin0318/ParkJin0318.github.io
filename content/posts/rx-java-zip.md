---
category: 개발
title: RxJava Zip 올바른 병렬처리
description: RxJava Zip 올바른 병렬처리에 대하여
createdAt: 2022.04.17
tags:
    - Android
    - RxJava
root: .components.layout.PostPageLayout
---

## Operator Zip
RxJava를 사용하면서 대부분 **여러 개의 스트림을 동시에 호출**하여 **결과 값을 한 번에 처리**하고 싶은 경우에 **Zip**이라는 Operator를 사용할 것이다.   
하지만 Zip은 모든 스트림의 결과가 발행이 되었을 때 실행되지만, 모든 스트림이 **동시에 호출되는가에 대해서**는 생각해봐야 한다.   

## 올바르지 않은 처리
``` kotlin
val firstWord = Observable.fromCallable { getFirstWord() }
val lastWord = Observable.fromCallable { getLastWord() }

Observable.zip(firstWord, lastWord) { first, last ->
	"$first $last!"
}.subscribeOn(Schedulers.io())
	.observeOn(AndroidSchedulers.mainThread())
	.subscribe { Log.e("result word", it) }
```

<img src="/images/rx_java_zip/result_bad.png" alt="database" width="80%"/>

예상대로라면 동시에 호출이 되어 동시에 호출이 되어야 하지만, Log를 찍어보면 **순차적으로 호출**이 되고 있는 것을 확인할 수 있다.   
물론 ```subscribeOn```에서 스레드를 io로 설정을 했으니 당연히 병렬로 처리되어야 하는 것이 아닌가 라고 생각할 수 있지만, Zip에 대한 스레드를 io로 설정한 것이지 **각각의 스트림에 대해서는 스레드를 지정해주지 않았기에** 위와 같은 결과를 확인할 수 있다.

## 올바른 처리
``` kotlin
val firstWord = Observable.fromCallable { getFirstWord() }
	.subscribeOn(Schedulers.io())
val lastWord = Observable.fromCallable { getLastWord() }
	.subscribeOn(Schedulers.io())

Observable.zip(firstWord, lastWord) { first, last ->
	"$first $last!"
}.observeOn(AndroidSchedulers.mainThread())
	.subscribe { Log.e("result word", it) }
```
<img src="/images/rx_java_zip/result_good.png" alt="database" width="80%"/>

각각의 스트림에 스레드를 지정해주니 예상한대로 **동시에 호출**되는 것을 확인 할 수 있다.   
위 코드에서는 Zip에서 큰 역할을 하고 있지 않아 따로 스레드를 지정해주고 있지 않지만, Zip의 역할이 커진다면 스레드를 지정해주는 것이 좋다.

## 끝내며
Zip을 사용할 때 순차적으로 호출하는 것을 의도한 경우를 제외하고는 각각의 스트림에 스레드를 지정해주는 것을 잊지 말자! 😊
