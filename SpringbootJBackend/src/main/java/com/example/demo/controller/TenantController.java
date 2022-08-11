package com.example.demo.controller;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Tenant;
import com.example.demo.repository.TenantRepository;
@RestController
@RequestMapping("/api/v1/")
@CrossOrigin
public class TenantController {
	@Autowired
	private  TenantRepository TenantRepository;
	
	@GetMapping("/tenants")
	public java.util.List<Tenant> getAllTenants()
	{
		return TenantRepository.findAll();
	}
	
	@PostMapping("/tenants")
	public Tenant createTenant(@RequestBody Tenant tenant)
	{
		return TenantRepository.save(tenant);
	}
	
	
	
	
	
	@GetMapping("/tenants/{id}")
	public ResponseEntity<Tenant> gettenantById(@PathVariable Long id)
	{
		Tenant tenant = TenantRepository.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Tenant does not Exist:"+id));
		return ResponseEntity.ok(tenant);
	}
	
	
	@PutMapping("/tenants/{id}")
	public ResponseEntity<Tenant> updateTenant(@PathVariable Long id,@RequestBody Tenant tenantDetails)
	{
		Tenant tenant = TenantRepository.findById(id).
				orElseThrow(()->new ResourceNotFoundException("Tenant does not Exist:"+id));
		
		
		tenant.setName(tenantDetails.getName());
		tenant.setEmail(tenantDetails.getEmail());
		
		Tenant updatedTenant=TenantRepository.save(tenant);
		return ResponseEntity.ok(updatedTenant);
	}
	
	@DeleteMapping("/tenants/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
		Tenant employee = TenantRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
		
		TenantRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);}

}
