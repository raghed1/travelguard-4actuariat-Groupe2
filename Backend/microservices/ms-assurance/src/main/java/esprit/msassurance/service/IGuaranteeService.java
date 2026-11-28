package esprit.msassurance.service;




import esprit.msassurance.entity.Guarantee;
import java.util.List;

public interface IGuaranteeService {

    Guarantee addGuarantee(Guarantee guarantee);

    List<Guarantee> getAllGuarantees();

    Guarantee getGuaranteeById(Long id);

    Guarantee updateGuarantee(Long id, Guarantee guarantee);

    void deleteGuarantee(Long id);
}

