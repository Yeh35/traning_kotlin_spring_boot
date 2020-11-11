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
    왜그런지는 모르겠지만 gradle.kts에서 막 빨간줄이 생기면서 아래와 같은 에러 메시지가 뜬다면 환경변수에서 `JavaHome`을 지우면 된다.
    이유는 왜그런지 모르겠다. 
    ```
    Cannot access 'java.lang.Comparable' which is a supertype of 'org.gradle.kotlin.dsl.KotlinBuildScript'. Check your module classpath for missing or conflicting dependencies
    ```


## 참고자료 
* [SpringBoot2로 Rest api 만들기](https://daddyprogrammer.org/post/19/spring-boot2-start-intellij/)
* ['Gradle Kotlin DSL' 이야기](https://woowabros.github.io/tools/2019/04/30/gradle-kotlin-dsl.html)