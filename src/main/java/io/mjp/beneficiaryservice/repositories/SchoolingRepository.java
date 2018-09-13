package io.mjp.beneficiaryservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.mjp.beneficiaryservice.domains.Schooling;

@Repository
public interface SchoolingRepository extends JpaRepository<Schooling, Long> {

}
