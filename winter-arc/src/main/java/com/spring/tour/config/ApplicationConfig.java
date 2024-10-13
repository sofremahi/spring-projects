package com.spring.tour.config;

import com.spring.tour.filter.RequestResponseFilter;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ApplicationConfig implements
        WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    @Override
    public void customize(ConfigurableServletWebServerFactory container) {
        container.setPort(8081);
        container.setContextPath("/spring-app");
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("my.gmail@gmail.com");
        mailSender.setPassword("password");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

//    @Bean
//    public static PropertySourcesPlaceholderConfigurer properties() {
//        PropertySourcesPlaceholderConfigurer pspc
//                = new PropertySourcesPlaceholderConfigurer();
//        Resource[] resources = new ClassPathResource[]
//                {new ClassPathResource("foo.properties")};
//        pspc.setLocations(resources);
//        pspc.setIgnoreUnresolvablePlaceholders(true);
//        return pspc;
//    }

    @Bean
    public FilterRegistrationBean<RequestResponseFilter> loggingFilter() {
        FilterRegistrationBean<RequestResponseFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new RequestResponseFilter());
        registrationBean.addUrlPatterns("/spring-app/homepage");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
