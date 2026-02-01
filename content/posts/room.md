---
category: 개발
title: 간단한 Room 사용 방법
description: 안드로이드 앱의 내부 DB로 Jetpack 라이브러리 중 하나입니다.
createdAt: 2021.01.20
tags:
    - Android
    - Room
    - Database
root: .components.layout.PostPageLayout
---

# Room이란?
안드로이드 앱의 내부 DB로 Jetpack 라이브러리 중 하나입니다.   
SQLite에 대한 추상화 레이어를 제공하여 원활한 데이터베이스 엑세스를 제공합니다.

<img src="/images/room/database.png" alt="database" width="50%"/>
- Database: 데이터베이스 홀더를 포함하며 앱의 지속적인 관계형 데이터의 기본 연결을 위한 기본 액세스 포인트 역할을 합니다.
- DAO: 데이터베이스에 액세스하는 데 사용되는 메서드가 포함되어 있습니다.
- Entites: 데이터베이스 내의 테이블을 나타냅니다.

# Room 사용방법
간단하게 메모장으로 사용할 수 있는 DB를 만들어보겠습니다.

### 빌드 환경 설정
build.gradle
```kotlin
plugins {
    ...
    id 'kotlin-kapt'
}
```
kotlin kapt 플러그인을 추가해줍니다.

```kotlin
dependencies {
    ...
    kapt "androidx.room:room-compiler:버전정보"
    testImplementation "androidx.room:room-testing:버전정보"
    implementation "androidx.room:room-runtime:버전정보"
}
```
room 관련 라이브러리를 추가해줍니다.

### Entity 생성
MemoEntity.kt
```kotlin
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_table")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true)
    val idx: Int,
    val title: String,
    val content: String
)
```
메모의 컬럼 정보를 저장할 테이블 객체를 만들어줍니다.

### Dao 생성
MemoDao.kt
```kotlin
import androidx.room.*

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMemo(entity: MemoEntity)

    @Query("SELECT * FROM memo_table")
    fun getAllMemo(): List<MemoEntity>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMemo(entity: MemoEntity)

    @Query("DELETE FROM memo_table WHERE idx=:idx")
    fun deleteMemo(idx : Int)

    @Query("DELETE FROM memo_table")
    fun deleteAllMemo()
}
```
데이터베이스에 접근 가능한 쿼리를 제공해주는 Dao를 만들어줍니다.

### Database 생성
```kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MemoEntity::class], version = 1, exportSchema = false)
abstract class MemoDatabase : RoomDatabase() {

    abstract fun memoDao() : MemoDao

    companion object {
        private var INSTANCE : MemoDatabase? = null

        fun getInstance(context: Context) : MemoDatabase? {
            if (INSTANCE == null) {
                synchronized(MemoDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MemoDatabase::class.java, "memo-db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}
```
데이터베이스를 엑세스 할 수 있으며, Entity와 버전 정보를 관리해줄 Database를 만들어줍니다.

### Room 적용
```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var memoDao: MemoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        accessDatabase()
        Thread(insertMemo).start()
    }
    
    private fun accessDatabase() {
        val memoDatabase = MemoDatabase.getInstance(this)!!
        memoDao = memoDatabase.memoDao()
    }

    private val insertMemo = Runnable {
        val entity = MemoEntity(
            idx = 0,
            title = "Room 사용방법",
            content = "정말 쉬워요!"
        )
        memoDao.insertMemo(entity)
    }
}
```
위와 같은 방식으로 Thread를 이용하여 쉽게 사용할 수 있습니다. 그리고


```kotlin
runOnUiThread { 
    // ...
}
```
runOnUiThread를 사용하면 Thread 사용할 때 뷰를 안전하게 접근할 수 있습니다.

# 끝내며
확실히 SQLite에 비해 Room은 쿼리 유효성을 컴파일 중에 검사할 수 있어서 편리했습니다.    
이번 글에서는 Room을 그냥 사용해봤는데, Room은 다양한 반환 유형을 지원하기 때문에 RxJava나 코루틴 등을 이용한 비동기처리가 가능합니다.
