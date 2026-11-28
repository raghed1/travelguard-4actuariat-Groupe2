package esprit.msassurance.controller;


import esprit.msassurance.entity.Guarantee;
import esprit.msassurance.service.IGuaranteeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guarantees")
@CrossOrigin("*")
public class GuaranteeController {

    private final IGuaranteeService guaranteeService;

    public GuaranteeController(IGuaranteeService guaranteeService) {
        this.guaranteeService = guaranteeService;
    }

    // CREATE
    @PostMapping
    public Guarantee addGuarantee(@RequestBody Guarantee guarantee) {
        return guaranteeService.addGuarantee(guarantee);
    }

    // READ ALL
    @GetMapping
    public List<Guarantee> getAllGuarantees() {
        return guaranteeService.getAllGuarantees();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Guarantee getGuaranteeById(@PathVariable Long id) {
        return guaranteeService.getGuaranteeById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public Guarantee updateGuarantee(
            @PathVariable Long id,
            @RequestBody Guarantee guarantee) {
        return guaranteeService.updateGuarantee(id, guarantee);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteGuarantee(@PathVariable Long id) {
        guaranteeService.deleteGuarantee(id);
    }
}