package com.example.user_service.service.impl;

import com.example.user_service.entity.User;
import com.example.user_service.service.KeyCloakService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class KeyCloakServiceImpl implements KeyCloakService {
    private final RestTemplate restTemplate;

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client.id}")
    private String clientId;

    @Value("${keycloak.client.secret}")
    private String clientSecret;

    public KeyCloakServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createUserInKeycloak(User user, String temporaryPassword) {

        String accessToken = getAccessToken();


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put("username", user.getUsername());
        userPayload.put("enabled", true);
        userPayload.put("email", user.getEmail());

        Map<String, Object> credential = new HashMap<>();
        credential.put("type", "password");
        credential.put("value", temporaryPassword);
        credential.put("temporary", true);

        userPayload.put("credentials", List.of(credential));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                keycloakUrl + "/admin/realms/" + realm + "/users",
                HttpMethod.POST,
                request,
                String.class
        );

        // Step 6: Handle the response
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Failed to create user in Keycloak: " + response.getBody());
        }

        System.out.println("User created successfully in Keycloak.");
    }

    private String getAccessToken() {
        Map<String, String> tokenRequestBody = new HashMap<>();
        tokenRequestBody.put("grant_type", "client_credentials");
        tokenRequestBody.put("client_id", clientId);
        tokenRequestBody.put("client_secret", clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(tokenRequestBody, headers);

        String keycloakTokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        ResponseEntity<Map> response = restTemplate.exchange(keycloakTokenUrl, HttpMethod.POST, requestEntity, Map.class);


        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (String) response.getBody().get("access_token");
        } else {
            throw new RuntimeException("Failed to obtain access token from Keycloak");
        }
    }
}
//package com.example.user_service.service.impl;
//
//import com.example.user_service.entity.User;
//import com.example.user_service.service.KeyCloakService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//public class KeyCloakServiceImpl implements KeyCloakService {
//    private final RestTemplate restTemplate;
//    @Value("${keycloak.url}")
//    private String keycloakUrl;
//
//    @Value("${keycloak.realm}")
//    private String realm;
//
//    @Value("${keycloak.client.id}")
//    private String clientId;
//
//    @Value("${keycloak.redirect.uri}")
//    private String redirectUri;
//
////    @Value("${keycloak.client.secret}")
////    private String clientSecret;
//
//    public KeyCloakServiceImpl(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//
//    public void createUserInKeycloak(User user, String temporaryPassword) throws NoSuchAlgorithmException {
//        String codeVerifier = generateCodeVerifier();
//        String codeChallenge = generateCodeChallenge(codeVerifier);
//
//        String authorizationCode = getAuthorizationCodeFromUser(codeChallenge);
//        String accessToken = getAccessTokenWithAuthorizationCode(authorizationCode, codeVerifier);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth(accessToken);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        Map<String, Object> userPayload = new HashMap<>();
//        userPayload.put("username", user.getUsername());
//        userPayload.put("enabled", true);
//        userPayload.put("email", user.getEmail());
//
//        Map<String, Object> credential = new HashMap<>();
//        credential.put("type", "password");
//        credential.put("value", temporaryPassword);
//        credential.put("temporary", true);
//
//        userPayload.put("credentials", new Map[]{credential});
//
//        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userPayload, headers);
//
//        ResponseEntity<String> response = restTemplate.exchange(
//                keycloakUrl + "/admin/realms/" + realm + "/users",
//                HttpMethod.POST,
//                request,
//                String.class
//        );
//
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            throw new RuntimeException("Failed to create user in Keycloak: " + response.getBody());
//        }
//
//        System.out.println("User created successfully in Keycloak.");
//    }
//
//    private String getAuthorizationCodeFromUser(String codeChallenge) {
//        String keycloakAuthUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/auth"
//                + "?client_id=" + clientId
//                + "&response_type=code"
//                + "&redirect_uri=" + redirectUri
//                + "&scope=openid"
//                + "&code_challenge=" + codeChallenge
//                + "&code_challenge_method=S256";
//
//        // In a real application, this would involve redirecting the user to the Keycloak URL and handling the redirect response to get the authorization code
//        System.out.println("Redirect user to Keycloak: " + keycloakAuthUrl);
//
//        // For demonstration purposes, we'll assume you get the authorization code from the response
//        return "received-authorization-code";
//    }
//
//    // Exchanges authorization code for access token (Step 3)
//    private String getAccessTokenWithAuthorizationCode(String authorizationCode, String codeVerifier) {
//        Map<String, String> tokenRequestBody = new HashMap<>();
//        tokenRequestBody.put("grant_type", "authorization_code");
//        tokenRequestBody.put("client_id", clientId);
//        tokenRequestBody.put("code", authorizationCode);
//        tokenRequestBody.put("redirect_uri", redirectUri);
//        tokenRequestBody.put("code_verifier", codeVerifier);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(tokenRequestBody, headers);
//
//        String keycloakTokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";
//
//        ResponseEntity<Map> response = restTemplate.exchange(keycloakTokenUrl, HttpMethod.POST, requestEntity, Map.class);
//
//        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//            return (String) response.getBody().get("access_token");
//        } else {
//            throw new RuntimeException("Failed to obtain access token from Keycloak");
//        }
//    }
//
//
//    public static String generateCodeVerifier() {
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(new byte[32]);
//    }
//
//    public static String generateCodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
//        byte[] bytes = codeVerifier.getBytes(StandardCharsets.US_ASCII);
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        byte[] digest = md.digest(bytes);
//        return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
//    }
//}
