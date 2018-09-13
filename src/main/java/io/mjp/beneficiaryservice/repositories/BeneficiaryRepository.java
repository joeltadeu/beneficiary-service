package io.mjp.beneficiaryservice.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mjp.beneficiaryservice.domains.Beneficiary;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

	Optional<Beneficiary> findByCpf(String cpf);

}
