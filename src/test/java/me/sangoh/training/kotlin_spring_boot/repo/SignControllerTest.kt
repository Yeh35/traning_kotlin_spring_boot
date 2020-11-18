package me.sangoh.training.kotlin_spring_boot.repo

import me.sangoh.training.kotlin_spring_boot.entity.User
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SignControllerTest() {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userJpaRepo: UserJpaRepo

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Before
    fun setUp() {
        userJpaRepo.save(User(
                uid = "happydaddy@naver.com",
                name = "happydaddy",
                password = passwordEncoder.encode("1234"),
                roles = Collections.singletonList("ROLE_USER")))
    }

    @Test
    @Throws(Exception::class)
    fun signin() {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("id", "happydaddy@naver.com")
        params.add("password", "1234")
        mockMvc.perform(post("/v1/signin")
                .params(params))
                .andDo {
                    print(it)
                }
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
                .andExpect(jsonPath("$.data").exists())
    }

    @Test
    @Throws(Exception::class)
    fun signup() {
        val epochTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond() / 2
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("id", "happydaddy_$epochTime@naver.com")
        params.add("password", "12345")
        params.add("name", "happydaddy_$epochTime")

        mockMvc.perform(post("/v1/signup").params(params))
                .andDo {
                    print(it)
                }
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.msg").exists())
    }


}