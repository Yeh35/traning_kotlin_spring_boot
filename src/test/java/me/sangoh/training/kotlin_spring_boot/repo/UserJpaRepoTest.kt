package me.sangoh.training.kotlin_spring_boot.repo

import junit.framework.Assert.*
import me.sangoh.training.kotlin_spring_boot.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@DataJpaTest
class UserJpaRepoTest(
        private val userJpaRepo: UserJpaRepo,
        private val passwordEncoder: PasswordEncoder
) {

    @Test
    fun whenFindByUid_thenReturnUser() {
        val uid = "angrydaddy@gmail.com"
        val name = "angrydaddy"

        // given
        userJpaRepo.save(User(
                uid = uid,
                password = passwordEncoder.encode("1234"),
                name = name,
                roles = Collections.singletonList("ROLE_USER")
        ))

        // when
        val user = userJpaRepo.findByUid(uid)

        // then
        assertNotNull(user) // user객체가 null이 아닌지 체크
        assertTrue(user.isPresent) // user객체가 존재여부 true/false 체크
        assertEquals(user.get().name, name) // user객체의 name과 name변수 값이 같은지 체크
    }
}