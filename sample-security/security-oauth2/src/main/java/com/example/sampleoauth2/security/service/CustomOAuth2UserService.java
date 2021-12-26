package com.example.sampleoauth2.security.service;

import com.example.sampleoauth2.domain.OAuth2Entity;
import com.example.sampleoauth2.repository.OAuth2Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OAuth2Repository oAuth2Repository;

    private RestTemplate restTemplate = new RestTemplate();
    private final Converter<OAuth2UserRequest, RequestEntity<?>> requestEntityConverter = new OAuth2UserRequestEntityConverter();
//    private static final ParameterizedTypeReference<Map<String, Object>> PARAMETERIZED_RESPONSE_TYPE
//            = new ParameterizedTypeReference<Map<String, Object>>() {
//    };


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // userRequest객체를 request객체로 변환해 Resouce Server에 요청을 보내 User 정보를 가져온다.
        RequestEntity<?> request = this.requestEntityConverter.convert(userRequest);
        ResponseEntity<Map<String, Object>> response
                = restTemplate.exchange(request, new ParameterizedTypeReference<Map<String, Object>>() {});

        Map<String, Object> userAttributes = response.getBody();

        // 사용자 권한 목록을 저장한다.
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new OAuth2UserAuthority(userAttributes));

        // token값으로 접근 가능한 scope를 같이 저장한다.
        OAuth2AccessToken token = userRequest.getAccessToken();
        for (String authority : token.getScopes()) {
            authorities.add(new SimpleGrantedAuthority("SCOPE_" + authority));
        }

        saveOrUpdateOAuth2Entity(userAttributes);
        OAuth2User oAuth2User = new DefaultOAuth2User(authorities, userAttributes, userNameAttributeName);
        return oAuth2User;
    }

    private void saveOrUpdateOAuth2Entity( Map<String, Object> userAttributes) {
        OAuth2Entity oAuth2Entity = oAuth2Repository.findOAuth2EntityByUsername((String) userAttributes.get("email"))
                .map(entity -> entity.updateUserName((String) userAttributes.get("name")))
                .orElse(OAuth2Entity.builder()
                        .username((String) (String) userAttributes.get("email"))
                        .email((String) userAttributes.get("name"))
                        .build());
        oAuth2Repository.save(oAuth2Entity);
    }
}
