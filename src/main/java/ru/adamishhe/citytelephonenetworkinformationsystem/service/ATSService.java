package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ATS;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Organization;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.ATSRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ATSService {

    private final ATSRepository atsRepository;

    private final OrganizationService organizationService;

    public ATSService(ATSRepository atsRepository, OrganizationService organizationService) {
        this.atsRepository = atsRepository;
        this.organizationService = organizationService;
    }

    public Page<ATS> getAllATS(int page, int size) {
        return atsRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<ATS> getATSById(Long id) {
        return atsRepository.findById(id);
    }

    public List<Object[]> getMaxTotalDebt() {
        return atsRepository.findMaxTotalDebt();
    }

    public List<Object[]> getMaxDebtorsCount() {
        return atsRepository.findMaxDebtorsCount();
    }

    public List<Object[]> getMinDebtorsCount() {
        return atsRepository.findMinDebtorsCount();
    }

    public ATS createATS(ATS ats) {
        Organization organization = organizationService.getOrganizationById(ats.getOrganization().getId());
        ats.setOrganization(organization);
        return atsRepository.save(ats);
    }

    public ATS updateATS(Long id, ATS atsDetails) {
        ATS ats = atsRepository.findById(id).orElseThrow(() -> new RuntimeException("ATS not found"));

        if (atsDetails.getSerialNo() != null && !atsDetails.getSerialNo().isEmpty()) {
            ats.setSerialNo(atsDetails.getSerialNo());
        }
        if (atsDetails.getFirstPhoneNo() != null && !atsDetails.getFirstPhoneNo().isEmpty()) {
            ats.setFirstPhoneNo(atsDetails.getFirstPhoneNo());
        }
        if (atsDetails.getLastPhoneNo() != null && !atsDetails.getLastPhoneNo().isEmpty()) {
            ats.setLastPhoneNo(atsDetails.getLastPhoneNo());
        }
        if (atsDetails.getOrganization() != null) {
            Organization organization = organizationService.getOrganizationById(atsDetails.getOrganization().getId());
            ats.setOrganization(organization);
        }
        if (atsDetails.getType() != null) {
            ats.setType(atsDetails.getType());
        }

        return atsRepository.save(ats);
    }

    public void deleteATS(Long id) {
        ATS ats = atsRepository.findById(id).orElseThrow(() -> new RuntimeException("ATS not found"));
        atsRepository.delete(ats);
    }
}
