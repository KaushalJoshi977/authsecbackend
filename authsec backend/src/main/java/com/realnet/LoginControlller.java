package com.realnet;
import java.util.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import org.json.JSONObject;

@Controller
public class LoginControlller {

//	 @GetMapping("/loggedin")
//	    public String loggedin(@RequestParam("token") String token, Model model) {
//	        // Here, you can use the token as needed
//	        model.addAttribute("token", token);
//	        return "loggedin";
//	    }

	
    @GetMapping("/login1")
    public String showLoginForm() {
        return "login1";
    }
    

    @PostMapping("/login1")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        // Handle the login request

        RestTemplate restTemplate = new RestTemplate();
        String endpointUrl = "http://localhost:9292/token/session";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create a map with the email and password
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", username);
        requestBody.put("password", password);

        // Create the request entity with the map and headers
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, requestEntity, String.class);

        // Handle the response
        String responseBody = responseEntity.getBody();

        // Check if the operationStatus is "SUCCESS"
        JSONObject responseJson = new JSONObject(responseBody);
        String operationStatus = responseJson.getString("operationStatus");

        if (operationStatus.equals("SUCCESS")) {
            // Extract the token from the response
            JSONObject itemJson = responseJson.getJSONObject("item");
            String token = itemJson.getString("token");

            ApiCaller apiCaller = new ApiCaller();
            String apiUrl = "http://localhost:9292/fndMenu/menuloadbyuser";
//            ResponseEntity<String> response = apiCaller.callApiWithJwtToken(apiUrl, token);
//            String responseString = response.getBody();
//            System.out.print(responseString);
//            model.addAttribute("jsonData", responseString);
            ResponseEntity<String> response = apiCaller.callApiWithJwtToken(apiUrl, token);
            String responseString = response.getBody();

            // Parse the string into a list of menus using a JSON parser
            List<UserMenues> menus = parseMenusFromString(responseString);

            model.addAttribute("menus", menus);
            model.addAttribute("token", token);

            
          //  return "loggedin1";
            return "item1";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login1";
        }
    }


	private List<UserMenues> parseMenusFromString(String jsonString) {
		// TODO Auto-generated method stub
		ObjectMapper objectMapper = new ObjectMapper();

	    try {
	        return objectMapper.readValue(jsonString, new TypeReference<List<UserMenues>>() {});
	    } catch (Exception e) {
	        // Handle the exception appropriately
	        e.printStackTrace();
	        return null;
	    }
	}
}
