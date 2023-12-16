package com.Beskovica.tenantsystem.service;

import com.Beskovica.tenantsystem.model.Tenant;

import java.util.List;

public interface TenantService {
    public Tenant saveTenant(Tenant tenant);

    public List<Tenant> getAllTenants();




}
