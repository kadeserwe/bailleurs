package sn.ssi.sigmap.web.rest;

import sn.ssi.sigmap.service.BailleursService;
import sn.ssi.sigmap.web.rest.errors.BadRequestAlertException;
import sn.ssi.sigmap.service.dto.BailleursDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link sn.ssi.sigmap.domain.Bailleurs}.
 */
@RestController
@RequestMapping("/api")
public class BailleursResource {

    private final Logger log = LoggerFactory.getLogger(BailleursResource.class);

    private static final String ENTITY_NAME = "bailleursBailleurs";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BailleursService bailleursService;

    public BailleursResource(BailleursService bailleursService) {
        this.bailleursService = bailleursService;
    }

    /**
     * {@code POST  /bailleurs} : Create a new bailleurs.
     *
     * @param bailleursDTO the bailleursDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bailleursDTO, or with status {@code 400 (Bad Request)} if the bailleurs has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bailleurs")
    public ResponseEntity<BailleursDTO> createBailleurs(@Valid @RequestBody BailleursDTO bailleursDTO) throws URISyntaxException {
        log.debug("REST request to save Bailleurs : {}", bailleursDTO);
        if (bailleursDTO.getId() != null) {
            throw new BadRequestAlertException("A new bailleurs cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BailleursDTO result = bailleursService.save(bailleursDTO);
        return ResponseEntity.created(new URI("/api/bailleurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bailleurs} : Updates an existing bailleurs.
     *
     * @param bailleursDTO the bailleursDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bailleursDTO,
     * or with status {@code 400 (Bad Request)} if the bailleursDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bailleursDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bailleurs")
    public ResponseEntity<BailleursDTO> updateBailleurs(@Valid @RequestBody BailleursDTO bailleursDTO) throws URISyntaxException {
        log.debug("REST request to update Bailleurs : {}", bailleursDTO);
        if (bailleursDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BailleursDTO result = bailleursService.save(bailleursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bailleursDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /bailleurs} : get all the bailleurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bailleurs in body.
     */
    @GetMapping("/bailleurs")
    public ResponseEntity<List<BailleursDTO>> getAllBailleurs(Pageable pageable) {
        log.debug("REST request to get a page of Bailleurs");
        Page<BailleursDTO> page = bailleursService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bailleurs/:id} : get the "id" bailleurs.
     *
     * @param id the id of the bailleursDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bailleursDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bailleurs/{id}")
    public ResponseEntity<BailleursDTO> getBailleurs(@PathVariable Long id) {
        log.debug("REST request to get Bailleurs : {}", id);
        Optional<BailleursDTO> bailleursDTO = bailleursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bailleursDTO);
    }

    /**
     * {@code DELETE  /bailleurs/:id} : delete the "id" bailleurs.
     *
     * @param id the id of the bailleursDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bailleurs/{id}")
    public ResponseEntity<Void> deleteBailleurs(@PathVariable Long id) {
        log.debug("REST request to delete Bailleurs : {}", id);
        bailleursService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
