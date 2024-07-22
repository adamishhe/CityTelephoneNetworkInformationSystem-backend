package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ATS;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.ATSService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ats")
public class ATSController {

    private final ATSService atsService;

    public ATSController(ATSService atsService) {
        this.atsService = atsService;
    }

    @GetMapping("/max-total-debt")
    public List<Object[]> getMaxTotalDebt() {
        return atsService.getMaxTotalDebt();
    }

    @GetMapping("/max-debtors-count")
    public List<Object[]> getMaxDebtorsCount() {
        return atsService.getMaxDebtorsCount();
    }

    @GetMapping("/min-debtors-count")
    public List<Object[]> getMinDebtorsCount() {
        return atsService.getMinDebtorsCount();
    }

    @GetMapping
    public Page<ATS> getAllATS(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return atsService.getAllATS(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ATS> getATSById(@PathVariable Long id) {
        Optional<ATS> ats = atsService.getATSById(id);
        return ats.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ATS createATS(@RequestBody ATS ats) {
        return atsService.createATS(ats);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ATS> updateATS(@PathVariable Long id, @RequestBody ATS atsDetails) {
        ATS updatedATS = atsService.updateATS(id, atsDetails);
        return ResponseEntity.ok(updatedATS);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteATS(@PathVariable Long id) {
        atsService.deleteATS(id);
        return ResponseEntity.noContent().build();
    }
}