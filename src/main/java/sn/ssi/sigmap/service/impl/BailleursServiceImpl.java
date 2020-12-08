package sn.ssi.sigmap.service.impl;

import sn.ssi.sigmap.service.BailleursService;
import sn.ssi.sigmap.domain.Bailleurs;
import sn.ssi.sigmap.repository.BailleursRepository;
import sn.ssi.sigmap.service.dto.BailleursDTO;
import sn.ssi.sigmap.service.mapper.BailleursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Bailleurs}.
 */
@Service
@Transactional
public class BailleursServiceImpl implements BailleursService {

    private final Logger log = LoggerFactory.getLogger(BailleursServiceImpl.class);

    private final BailleursRepository bailleursRepository;

    private final BailleursMapper bailleursMapper;

    public BailleursServiceImpl(BailleursRepository bailleursRepository, BailleursMapper bailleursMapper) {
        this.bailleursRepository = bailleursRepository;
        this.bailleursMapper = bailleursMapper;
    }

    @Override
    public BailleursDTO save(BailleursDTO bailleursDTO) {
        log.debug("Request to save Bailleurs : {}", bailleursDTO);
        Bailleurs bailleurs = bailleursMapper.toEntity(bailleursDTO);
        bailleurs = bailleursRepository.save(bailleurs);
        return bailleursMapper.toDto(bailleurs);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BailleursDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Bailleurs");
        return bailleursRepository.findAll(pageable)
            .map(bailleursMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<BailleursDTO> findOne(Long id) {
        log.debug("Request to get Bailleurs : {}", id);
        return bailleursRepository.findById(id)
            .map(bailleursMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bailleurs : {}", id);
        bailleursRepository.deleteById(id);
    }
}
