---
category: 개발
title: Apollo GraphQL 간단 사용법
description: GraphQL을 사용하기 편리하게 만들어주는 라이브러리입니다.
createdAt: 2021.01.30
tags:
    - Android
    - Apollo
    - GraphQL
root: .components.layout.PostPageLayout
---

# Apollo란?
GraphQL을 사용하기 편리하게 만들어주는 라이브러리입니다.

# Apollo 사용방법
https://rickandmortyapi.com/graphql


GraphQL을 사용해보기 위해 테스트 서버로 위 주소를 사용하겠습니다.

### Apollo Client 설치
```
brew install apollo-cli
```
MacOS의 경우 brew를 통해 apollo Client를 설치해주면 편리합니다.
혹은 npm을 통해 설치도 가능합니다.

### Android 빌드환경 설정

**classpath 추가**
```kotlin
buildscript { 
    ...
    dependencies {
        ...
        classpath "com.apollographql.apollo:apollo-gradle-plugin:2.5.2"
    }
}
```
**plugin 추가**
```kotlin
plugins {
    ...
    id 'com.apollographql.apollo'
}
```

**generateKotlinModels 활성화**
```kotlin
android {
    ...
    apollo {
        generateKotlinModels = true
    }
}
```

**라이브러리 추가**
```kotlin
dependencies {
    ...
    implementation "com.apollographql.apollo:apollo-runtime:2.5.2"
    implementation "com.apollographql.apollo:apollo-android-support:2.5.2"
}
```


### GraphQL Package 생성
<img src="/images/apollo/package.png" alt="database" width="50%"/>
안드로이드 스튜디오에서 위 이미지와 같이 java 패키지와 비슷하게 main에 GraphQL 패키지를 만들어주면 됩니다.

### schema.json 다운로드
<img src="/images/apollo/schema.png" alt="database" width="80%"/>
```
apollo schema:download --endpoint=https://rickandmortyapi.com/graphql schema.json
```
안드로이드 스튜디오 내부 터미널에서 위 명령어를 입력해주면 방금 만든 GraphQL 패키지에 _schema.json_ 파일이 저장됩니다.

```
apollo schema:download --endpoint=http://localhost:8080/graphql --header="Authorization: Bearer <token>"
```
서버에서 token을 요청할 경우 위와 같이 header 넣어주시면 됩니다.

### Query 파일 생성
<img src="/images/apollo/query.png" alt="database" width="50%"/>

**result.graphql**
```graphql
query Result {
  characters {
    results {
      name
      species
      gender
      image
    }
  }
}
```
파일을 생성하고 쿼리를 입력해줍니다.
그리고 Rebuild Project를 꼭 해줍시다.

### Request 요청

```kotlin
val apolloClient = ApolloClient.builder()
        .serverUrl("https://rickandmortyapi.com/graphql/")
        .build()

apolloClient.query(ResultQuery())
    .enqueue(object : ApolloCall.Callback<ResultQuery.Data>() {
        override fun onResponse(response: Response<ResultQuery.Data>) {
            ...
        }

        override fun onFailure(e: ApolloException) {
            ...
        }
    })
```

# 끝내며
RestAPI 라이브러리인 Retrofit2에 비해 초기 설정이 복잡하지만, RxJava나 코루틴을 같이 사용가능하며, Request 요청하는 부분이 간단하고, 필요한 데이터만 받을 수 있어 효율적으로 느껴졌습니다.

전체 코드는 [Github](https://github.com/ParkJin0318/GraphQL-Sample_Android)를 참고해주세요.
