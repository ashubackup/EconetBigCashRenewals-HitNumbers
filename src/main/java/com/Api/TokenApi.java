package com.Api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Entity.TblLogInInfor;
import com.Repository.LoginInfoRepo;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.http.HttpEntity;
@Service
public class TokenApi {

    @Value("${tokenApiUrl}")
    private String tokenUrl;

    @Value("${tokenApiUsername}")
    private String username;

    @Value("${tokenApiPassword}")
    private String password;

    // this is request PARAMETER
    @Value("${grantType}")
    private String grantType;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private LoginInfoRepo loginInfoRepo;

    public String generateToken() {
        String responseString = "";

        try {
        	
        	TblLogInInfor logInInfo = loginInfoRepo.findByStatus("1");
            String credentials = username + ":" + password;
            String encodedCredentials = java.util.Base64.getEncoder().encodeToString(credentials.getBytes());

            // Set request headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Connection", "keep-alive");
            headers.add("Authorization", "Basic "+encodedCredentials);
            headers.add("Content-Type", "application/x-www-form-urlencoded");
            headers.add("Accept-Encoding", "application/gzip");
            headers.add("Accept", "*/*");

            // Set request body
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", grantType);

//            // Set up RestTemplate
//            RestTemplate restTemplate = new RestTemplate();

            // Build request entity
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);


            // Make the HTTP request
            ResponseEntity<String> responseEntity = restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, String.class);

            // Process the response
            int statusCode = responseEntity.getStatusCodeValue();
            String responseBody = responseEntity.getBody();

//            System.out.println("response from token api"+responseBody);
            JSONObject json = new JSONObject(responseBody);
            System.out.println("The value of token "+json.getString("access_token"));
            // Print the response
            System.out.println("Response Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
            logInInfo.setToken(json.getString("access_token").toString());
            loginInfoRepo.save(logInInfo);
            System.out.println("");
            responseString = json.getString("access_token");
            System.out.println("Token Updated!!!");

            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseString;
    }
}
