package sn.ssi.sigmap.service;

import sn.ssi.sigmap.service.dto.BailleursDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link sn.ssi.sigmap.domain.Bailleurs}.
 */
public interface BailleursService {

    /**
     * Save a bailleurs.
     *
     * @param bailleursDTO the entity to save.
     * @return the persisted entity.
     */
    BailleursDTO save(BailleursDTO bailleursDTO);

    /**
     * Get all the bailleurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BailleursDTO> findAll(Pageable pageable);


    /**
     * Get the "id" bailleurs.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BailleursDTO> findOne(Long id);

    /**
     * Delete the "id" bailleurs.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
