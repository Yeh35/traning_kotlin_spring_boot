# 코틀린을 이용한 Spring boot 연습
코틀린 + spring boot2를 이용한 Rest Api 서버 구성 연습

## Gradle
Gradle 5.0 이후 `Gradle Kotlin DSL`을 지원한다.
Kotlin 프로젝트인지 Gradle 프로젝트인지는 확장자로 구분한다. 
* Groovy DSL: .gradle -> build.gradle, settings.gradle
* Kotlin DSL: .gradle.kts -> build.gradle.kts, settings.gradle.kts

기존 groovy를 사용했을 때는 Intellij Community에서 자동완성을 지원하지 않았는데 Kotlin은 지원해준다.
하지만.. 그렇다고 그렇게 좋게 지원하지는 않는다...

* 디버깅       
    1. 왜그런지는 모르겠지만 gradle.kts에서 막 빨간줄이 생기면서 아래와 같은 에러 메시지가 뜬다면 환경변수에서 `JavaHome`을 지우면 된다.
    이유는 왜그런지 모르겠다. 
    ```
    Cannot access 'java.lang.Comparable' which is a supertype of 'org.gradle.kotlin.dsl.KotlinBuildScript'. Check your module classpath for missing or conflicting dependencies
    ```
  
    2. `JavaHome`을 지워도 계속 같은 문제가 발생했는데 `Project Structure -> SDKs`에 가서 사용안하는 SDK를 모두 지우니 해결 되었다.  
    https://youtrack.jetbrains.com/issue/KT-40642
    

## Main 함수

```kotlin
@SpringBootApplication
class SpringApplication

fun main(args: Array<String>) {
    runApplication<SpringApplication>(*args)
}
```
코틀린에서는 Main 함수를 static 키워드 사용하지 않고도 만들 수 있다.
그저 main 함수를 선언만 하면 된다.
    
## Controller 

```kotlin
@Controller
class HelloController {

    @GetMapping(value = ["/helloworld/string"])
    @ResponseBody
    fun helloworldString(): String {
        return "hello world"
    }

    @GetMapping(value = ["/helloworld/json"])
    @ResponseBody
    fun helloworldJson(): Hello {
        return Hello("hello world")
    }

    @GetMapping(value = ["/hellowerld/page"])
    fun helloworldPage(): String {
        return "helloworld"
    }

    data class Hello(
        val message: String
    )
}
```
어노테이션을 사용하여 url을 지정하고 어떤식으로 Return할지 정하는 것은 동일하였다.

## Entity

```kotlin 
@Table(name = "user")
@Entity
class User (
        msrl: Long = 0,
        uid: String,
        name: String
) {
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        var msrl: Long = 0
                private set

        @Column(nullable = false, unique = true, length = 30)
        var uid: String
                private set

        @Column(nullable = false, length = 100)
        var name: String
                private set

        init {
                this.msrl = msrl
                this.uid = uid
                this.name = name
        }
}
```
Kotlin으로 넘어오면서 Lombok을 사용하지 않아도 되었다.   
그럼 어떻게 원하는 값만 getter를 만들 수 있을까? 
고민을 하였는데 [stackoverflow](https://stackoverflow.com/questions/48998907/kotlin-only-getter-pulblic-private-setter)에서 
답을 얻을 수 있었다.  

```kotlin
var name: String
        private set
```
setter를 감추고 싶으면 `private set`을 하면 되었다.

```kotlin
class User (
        msrl: Long = 0,
        uid: String,
        name: String
)

// 사용
val user = User(
                uid = uid,
                name = name)
```
원하는 Builder 패턴도 사용할 필요 없이 어떤 값을 어떻게 넣을 것인지 언어차원에서 보여주는 기능이 있었다.

## Swagger로 자동 문서 출력
설정하는 것을 보고싶다면 config/SwaggerConfiguration을 확인하면 된다.
사용방법은 아래와 같다.
```kotlin
    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping(value = ["/user"])
    fun save(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam uid: String,
            @ApiParam(value = "회원 이름", required = true) @RequestParam name: String
    ): User {
        val user = User(
                uid = uid,
                name = name)

        return userJpaRepo.save(user)
    }
```
`@ApiOperation`는 해당 호출의 이름과 설명을 작성하고
`@ApiParam`는 파라미터에 대한 설명을 작성한다.

## API 설계시
API의 특성상 한번 배포되고 공유된 API는 변경하기 힘들다.
Restful, 결과 데이터 구조를 표준화하게 설계를 해야한다.

기존 USER 정보
```json5

{
    "msrl": 1,
    "uid": "yumi@naver.com",
    "name": "정유미"
}
```    

표준화한 USER 정보 
```json5

{
  "data": {
    "msrl": 1,
    "uid": "yumi@naver.com",
    "name": "정유미"
  },
  "success": true
  "code": 0,
  "message": "성공하였습니다."
}
```



 

## 참고자료 
* [SpringBoot2로 Rest api 만들기](https://daddyprogrammer.org/post/19/spring-boot2-start-intellij/)
* ['Gradle Kotlin DSL' 이야기](https://woowabros.github.io/tools/2019/04/30/gradle-kotlin-dsl.html)