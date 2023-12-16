package com.Beskovica.tenantsystem.controller;

import com.Beskovica.tenantsystem.model.ResponseMessage;
import com.Beskovica.tenantsystem.model.Tenant;

import com.Beskovica.tenantsystem.repository.TenantRepository;
import com.Beskovica.tenantsystem.service.OpenAIService;
import com.Beskovica.tenantsystem.service.TenantService;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tenant")
@CrossOrigin
public class TenantController {
    @Autowired
    private TenantService tenantService;

    @Autowired
    private OpenAIService openAIService;

    @Autowired
    private TenantRepository tenantRepository;



    @PostMapping("/add")
    public String add(@RequestBody Tenant tenant) {
        tenant.setBalance(tenant.getMonthRent());

        tenantService.saveTenant(tenant);
        return "New Tenant is added";
    }

    @GetMapping("/getAll")
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }


    @PutMapping("/updateBalanceByName")
    public ResponseEntity<?> updateBalanceByName(@RequestBody Tenant tenant) {
        try {
            List<Tenant> tenants = tenantRepository.findByName(tenant.getName());

            if (tenants.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("Error: Tenant not found!"), HttpStatus.NOT_FOUND);
            }

            Tenant existingTenant = tenants.get(0);  // We'll assume there's only one tenant with that name.
            existingTenant.setBalance(existingTenant.getBalance() - tenant.getDepositAmount());  // Increasing balance.
            tenantRepository.save(existingTenant);

            return new ResponseEntity<>(new ResponseMessage("Balance updated successfully!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Server Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteByName")
    public ResponseEntity<?> deleteTenantByName(@RequestBody Tenant tenant) {
        try {
            List<Tenant> tenants = tenantRepository.findByName(tenant.getName());

            if (tenants.isEmpty()) {
                return new ResponseEntity<>(new ResponseMessage("Error: Tenant not found!"), HttpStatus.NOT_FOUND);
            }

            Tenant existingTenant = tenants.get(0);  // We'll assume there's only one tenant with that name.
            tenantRepository.delete(existingTenant);

            return new ResponseEntity<>(new ResponseMessage("Tenant deleted successfully!"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseMessage("Server Error!"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PostMapping("/ask")
    public ResponseEntity<Map<String, Object>> askOpenAI(@RequestBody String userQuestion) {
        // Delegate the interaction to the service
        Map<String, Object> responseFromGPT3 = openAIService.interactWithGPT3(userQuestion);

        // Return the structured response
        return ResponseEntity.ok(responseFromGPT3);
    }




}