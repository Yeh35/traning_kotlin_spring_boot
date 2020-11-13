package me.sangoh.training.kotlin_spring_boot.config

import net.rakugakibox.util.YamlResourceBundle
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import java.util.*
import java.util.Locale
import java.util.MissingResourceException


@Configuration
class MessageConfiguration: WebMvcConfigurer {

    @Bean
    fun localeResolver(): SessionLocaleResolver {
        val slr = SessionLocaleResolver()
        slr.setDefaultLocale(Locale.KOREAN)
        return slr
    }

    @Bean
    fun localeChangeInterceptor(): LocaleChangeInterceptor {
       val lci = LocaleChangeInterceptor()
        lci.paramName = "lang"
        return lci
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(localeChangeInterceptor())
    }

    @Bean
    fun messageSource(
            @Value("\${spring.messages.basename}") basename: String,
            @Value("\${spring.messages.encoding}") encoding: String
    ): MessageSource {
        val ms = YamlMessageSource()
        ms.setBasename(basename)
        ms.setDefaultEncoding(encoding)
        ms.setAlwaysUseMessageFormat(true)
        ms.setUseCodeAsDefaultMessage(true)
        ms.setFallbackToSystemLocale(true)
        return ms
    }

    // locale 정보에 따라 다른 yml 파일을 읽도록 처리
    class YamlMessageSource internal constructor() : ResourceBundleMessageSource() {

        @Throws(MissingResourceException::class)
        override fun doGetBundle(basename: String, locale: Locale): ResourceBundle {
            return ResourceBundle.getBundle(basename, locale, YamlResourceBundle.Control.INSTANCE)
        }
    }
}