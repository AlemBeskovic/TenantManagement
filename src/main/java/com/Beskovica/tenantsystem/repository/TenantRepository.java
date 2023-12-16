package com.Beskovica.tenantsystem.repository;

import com.Beskovica.tenantsystem.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
    List<Tenant> findByName(String name);


}
