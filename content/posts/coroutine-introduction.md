---
category: 개발
title: Kotlin Coroutine을 도입하면 좋은 이유
description: Kotlin Coroutine을 도입하면 좋은 이유에 대해여
createdAt: 2023.08.25
tags:
    - Android
    - Coroutine
root: .components.layout.PostPageLayout
---

단순한 이유로 코틀린 코루틴을 도입하고 싶을 수도 있고, 혹은 기존 프로젝트가 RxJava로 잘 구현되어 있고 문제 없이동작하는데,  

과연 리소스를 들여서 코틀린 코루틴을 도입해야 할까 같은 의문점을 가질 수 있습니다.  
동일한 생각을 했던 사람으로서 코루틴 코틀린을 도입하면 좋은 명확한 이유에 대해 정리해봤습니다.

## 비동기 연산을 처리하는 다양한 방법

### 이상적인 형태

```kotlin
fun main() {
    view.showProgressBar()

    val user = getUserFromApi() // API
    val posting = getPostingFromApi() // API

    view.hideProgressBar()
    view.showUI(user, posting)
}
```

"API로부터 사용자 정보와 게시글 정보를 가져와서 UI에 노출하는 로직"을 구현할 경우, 위와 같이 구현하는 것이 가장 읽기 쉬운 이상적인 형태일 것 같습니다.

다만, Android 환경에서는 아래와 같은 문제점이 있습니다.
- UI 스레드가 장시간 블로킹 되어 ANR로크래시가 발생할 것입니다.

다른 방법들을 살펴보면,

### Thread 이용

```kotlin
fun main() {
    view.showProgressBar()

    var user: User? = null
    var posting: Posting? = null

    val userThread = thread {
        user = getUserFromApi() // API
    }
    val postingThread = thread {
        posting = getPostingFromApi() // API
    }

    userThread.join()
    postingThread.join()

    view.hideProgressBar()
    view.showUI(user, posting)
}
```

블로킹 가능한 스레드를 생성하여 처리하고, 이후 UI 스레드로 전환하여 처리하는 것이 가장 직관적인 방법입니다.

다만, 다음과 같은 문제점이 있습니다.
- 스레드를 멈출 수 있는 방법이 없어 메모리 누수로 이어질 수 있습니다.
- 스레드를 많이 생성하면 비용이 많이 듭니다.
- 스레드를 자주 전환할 경우, 복잡도를 증가시키며 관리하기 어렵습니다.

### Callback 이용

```kotlin
fun main() {
    view.showProgressBar()

    getUserFromApi { user -> // API
        getPostingFromApi { posting -> // API

            view.hideProgressBar()
            view.showUI(user, posting)
        }
    }
}
```

함수를 논블로킹으로 만들고, 함수의 작업이 끝났을 때 호출될 콜백 함수를 넘겨주는 콜백으로 처리하는 방법도 있습니다.

다만, 다음과 같은 문제점이 있습니다.
- 콜백 또한 중간에 작업을 취소할 수 없습니다.
- 콜백 구조에서는 사용자 정보와 게시글 정보를 병렬로 동시에 처리할 수 없습니다.
- 들여쓰기가 많아져 코드가 읽기 어려워지는 콜백 지옥이 발생할 수 있습니다.
- 작업의 순서를 제어하기 힘듭니다.

### RxJava 이용

```kotlin
fun main() {
    view.showProgressBar()

    val userSingle = Single.fromCallable { getUserFromApi() } // API
        .subscribeOn(Schedulers.io())

    val postingSingle = Single.fromCallable { getPostingFromApi() } // API
        .subscribeOn(Schedulers.io())

    disposables += Single.zip(
        userSingle,
        postingSingle,
        { user, posting -> Pair(user, posting) }
    )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { (user, posting) ->

            view.hideProgressBar()
            view.showUI(user, posting)
        }
}
```

RxJava를 통해 메모리 누수가 없고, 취소가 가능하며, 스레드 또한 적절하게 사용할 수 있습니다.

다만, 다음과 같은 문제점이 있습니다.
- RxJava는 구현하기에 아주 복잡하며, 앞서 봤던 이상적인 코드의 형태와 완전히 다릅니다.
- RxJava의 Single, Observable 등으로 Wrapping 해야하므로 상당한 코드량이 변경되어야 할 수 있습니다.
- 병렬 처리를 위해 Pair와 같은 불필요한 처리가 필요할 수 있습니다.

### Coroutine 이용

```kotlin
fun main() {
    view.showProgressBar()

    scope.launch {
        val user = async { getUserFromApi() } // API
        val posting = async { getPostingFromApi() } // API

        view.hideProgressBar()
        view.showUI(user.awit(), posting.awit())
    }
}
```

코루틴을 통해 기존 코드의 큰 변경 없이 이상적인 형태의 코드와 비슷하게 작성 가능하며,
메모리 누수가 없고, 취소가 가능하게 사용 가능합니다.

코루틴 사용하면 스레드를 블로킹 하지 않고, 코루틴을 중단합니다.

코루틴의 중단은 데이터가 오는 걸 기다릴 때, 스레드를 블로킹 하는 대신 코루틴을 잠시 멈추는 방식으로 동작합니다.

코루틴이 잠시 멈춰 있는 동안, UI 스레드는 다른 작업을 할 수 있고, 데이터가 준비되면 코루틴은 다시 UI 스레드를 할당받아 이전에 멈춘 지점부터 다시 시작됩니다.

스레드를 명시적으로 생성하면 비용이 크고, 스레드를 위한 메모리 또한 할당 되어야 합니다.

해당 스레드가 응답을 기다릴 때마다 블로킹하고 있다면 비용이 많이 발생하겠지만, 코루틴을 통해 스레드를 블로킹하지 않고 코루틴을 중단하여 비교적 저렴한 비용으로 처리가 가능합니다.

추가적으로, KMP 환경에서도 코루틴을 지원하기에 앞으로 코루틴을 활용할 방안이 더 많아질 것 같습니다 🙂
