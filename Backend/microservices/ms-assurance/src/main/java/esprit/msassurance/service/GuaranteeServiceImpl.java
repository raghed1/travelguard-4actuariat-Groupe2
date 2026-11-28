package esprit.msassurance.service;


import esprit.msassurance.entity.Guarantee;
import esprit.msassurance.repository.GuaranteeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuaranteeServiceImpl implements IGuaranteeService {

    private final GuaranteeRepository guaranteeRepository;

    public GuaranteeServiceImpl(GuaranteeRepository guaranteeRepository) {
        this.guaranteeRepository = guaranteeRepository;
    }

    @Override
    public Guarantee addGuarantee(Guarantee guarantee) {
        return guaranteeRepository.save(guarantee);
    }

    @Override
    public List<Guarantee> getAllGuarantees() {
        return guaranteeRepository.findAll();
    }

    @Override
    public Guarantee getGuaranteeById(Long id) {
        return guaranteeRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Guarantee updateGuarantee(Long id, Guarantee guarantee) {
        Guarantee existingGuarantee = guaranteeRepository.findById(id)
                .orElse(null);

        if (existingGuarantee == null) {
            return null;
        }

        existingGuarantee.setName(guarantee.getName());
        existingGuarantee.setDescription(guarantee.getDescription());
        existingGuarantee.setCoverageType(guarantee.getCoverageType());

        return guaranteeRepository.save(existingGuarantee);
    }

    @Override
    public void deleteGuarantee(Long id) {
        guaranteeRepository.deleteById(id);
    }
}
