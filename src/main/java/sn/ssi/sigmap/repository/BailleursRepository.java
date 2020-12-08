package sn.ssi.sigmap.repository;

import sn.ssi.sigmap.domain.Bailleurs;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Bailleurs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BailleursRepository extends JpaRepository<Bailleurs, Long> {
}
