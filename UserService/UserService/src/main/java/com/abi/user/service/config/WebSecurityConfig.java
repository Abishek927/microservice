package com.abi.user.service.config;

import com.abi.user.service.config.interceptor.RestTemplateInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;
    @Autowired
    private OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        List<ClientHttpRequestInterceptor> clientHttpRequestInterceptorList=new ArrayList<>();
        clientHttpRequestInterceptorList.add(new RestTemplateInterceptor(oAuth2AuthorizedClientManager(clientRegistrationRepository,oAuth2AuthorizedClientRepository)));
        restTemplate.setInterceptors(clientHttpRequestInterceptorList);
        return new RestTemplate();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       httpSecurity.authorizeHttpRequests()
       .anyRequest()
       .authenticated()
       .and()
       .oauth2ResourceServer()
       .jwt();

       return httpSecurity.build();
    }

    OAuth2AuthorizedClientProvider oAuth2AuthorizedClientProvider= OAuth2AuthorizedClientProviderBuilder.builder().clientCredentials().build();


    //declare the bean of OAuth2AuthorizedClient manager bean
    @Bean
    public OAuth2AuthorizedClientManager oAuth2AuthorizedClientManager(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository){
        DefaultOAuth2AuthorizedClientManager defaultOAuth2AuthorizedClientManager=new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,oAuth2AuthorizedClientRepository);
        defaultOAuth2AuthorizedClientManager.setAuthorizedClientProvider(oAuth2AuthorizedClientProvider);
        return defaultOAuth2AuthorizedClientManager;

    }


}
