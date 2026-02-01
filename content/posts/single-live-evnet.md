---
category: 개발
title: SingleLiveEvnet와 Event Wrapper
description: 안드로이드 MVVM 패턴에서 단일 이벤트 발생과 액션을 처리하는 방법에 대하여
createdAt: 2021.01.03
tags:
    - Android
    - SingleLiveEvent
    - Event Wrapper
root: .components.layout.PostPageLayout
---

## SingleLiveEvent와 Event Wrapper란?
안드로이드 MVVM 패턴에서 SingleLiveEvent 클래스와 Event Wrapper 클래스를 통해 단일 이벤트 발생과 액션을 처리합니다.

## SingleLiveEvent와 Event Wrapper 사용하는 이유는?
MutableLiveData를 사용하게 된다면, 화면 회전과 같은 생명주기에 관여되는 이벤트가 발생시 LiveData를 여러번 구독하게 되는 문제점을 보완하기 위해 SingleLiveEvent와 Event Wrapper를 사용합니다.

## 사용방법은?

### SingleLiveEvent
**SingleLiveEvent**는 하나의 observer만 구독 가능합니다.   
만약, 여러개의 observer를 구독한다면 어느 것이 실행될지 알 수 없습니다.

#### Code
```kotlin
class SingleLiveEvent<T> : MutableLiveData<T?>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
        if (hasActiveObservers()) {
            Log.w(TAG, "Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }

    companion object {
        private const val TAG = "SingleLiveEvent"
    }
}
```
#### viewModel
```kotlin
    private val _onPlusEvent = SingleLiveEvent<Unit>()
    val onPlusEvent: LiveData<Unit>
    	get() = _onPlusEvent
        
    fun onPlusClick() {
    	_onPlusEvent.call()
    }
```
#### Activity
```kotlin
  with(viewModel) {
      onPlusEvent.observe(::getLifecycle) {
          // ...
      }
  }
```

### Event Wrapper
**Event Wrapper**는 사용자가 getContentIfNotHandled() 함수와 peekContent() 함수를 선택하여 명시적으로 사용할 수 있다는 장점을 가지고 있습니다.

**getContentIfNotHandled**를 사용하면 중복적인 이벤트 처리를 방지할 수 있고,    
**peekContent**를 사용하면 이벤트 처리 여부에 상관없이 값을 반환합니다.

#### code
```kotlin
/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}
```
#### ViewModel
```kotlin
    private val _onPlusEvent = MutableLiveData<Event<Boolean>>()
    val onPlusEvent: LiveData<Event<Boolean>>
        get() = _onPlusEvent
        
    fun onPlusClick() {
    	_onPlusEvent.call()
    }
```
#### Activity
```kotlin
  with(viewModel) {
      onPlusEvent.observe(::getLifecycle) {
          it.getContentIfNotHandled()?.let {
              // ...
          }
      }
  }
```

## 끝내며
SingleLiveEvent와 Event Wrapper를 모두 사용해봤지만, Event Wrapper가 SingleLiveEvent와 다르게 명시적으로 선택하여 코드를 짤 수 있다는 점이 매력적으로 느껴졌습니다.
