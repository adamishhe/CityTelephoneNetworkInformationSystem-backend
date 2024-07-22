package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.City;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Organization;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.OrganizationRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class OrganizationService {


    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Page<Organization> getAllOrganizations(Pageable pageable) {
        return organizationRepository.findAll(pageable);
    }

    public Organization createOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    public Organization updateOrganization(Long id, Organization organizationDetails) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(id);
        if (optionalOrganization.isPresent()) {
            Organization organization = optionalOrganization.get();
            if (organizationDetails.getName() != null && !organizationDetails.getName().isEmpty()) {
                organization.setName(organizationDetails.getName());
            }
            return organizationRepository.save(organization);
        } else {
            throw new RuntimeException("Organization not found with id " + id);
        }
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }

    public Organization getOrganizationById(Long id) {
        Optional<Organization> optionalOrganization = organizationRepository.findById(id);
        if (optionalOrganization.isPresent()) {
            return optionalOrganization.get();
        } else {
            throw new RuntimeException("Organization not found with id " + id);
        }
    }
}

