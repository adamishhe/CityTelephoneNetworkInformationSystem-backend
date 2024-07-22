package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Organization;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.OrganizationService;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {


    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public Page<Organization> getAllOrganizations(Pageable pageable) {
        return organizationService.getAllOrganizations(pageable);
    }

    @PostMapping
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long id, @RequestBody Organization organizationDetails) {
        Organization updatedOrganization = organizationService.updateOrganization(id, organizationDetails);
        return ResponseEntity.ok(updatedOrganization);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.ok().build();
    }
}
