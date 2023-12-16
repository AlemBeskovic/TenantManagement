package com.Beskovica.tenantsystem.service;

import com.Beskovica.tenantsystem.model.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    @Autowired
    private TenantService tenantService;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${modelId}")
    private String modelId;

    @Value("${url}")
    private String url;



    private String prepareDataForGPT3(List<Tenant> tenants) {
        StringBuilder sb = new StringBuilder();
        sb.append("Tenants Data:\n");
        for(Tenant tenant : tenants) {
            sb.append("- Name: ").append(tenant.getName())
                    .append(", Address: ").append(tenant.getAddress())
                    .append(", Town: ").append(tenant.getTown())
                    .append(", State: ").append(tenant.getState())
                    .append(", Apartment Number: ").append(tenant.getApartmentNumber())
                    .append(", Month Rent: ").append(tenant.getMonthRent())
                    .append(", Balance: ").append(tenant.getBalance())
                    .append("\n");
        }
        return sb.toString();
    }
    public Map<String, Object> interactWithGPT3(String userQuestion) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // Get tenant data and prepare the prompt for OpenAI
        List<Tenant> allTenants = tenantService.getAllTenants();
        String dataToSend = prepareDataForGPT3(allTenants);
        String prompt = dataToSend + "\n" + userQuestion;

        Map<String, Object> body = new HashMap<>();
        body.put("model", modelId);
        body.put("messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant."),
                Map.of("role", "user", "content", prompt)
        ));

        // Print the body to ensure it's constructed correctly
        System.out.println(body);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        // Extract the message content from the response
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        String content = (String) message.get("content");

        // Create a structured response map
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("answer", content);
        return responseBody; // Return the structured response
    }

}
