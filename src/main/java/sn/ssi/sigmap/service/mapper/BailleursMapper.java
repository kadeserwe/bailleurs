package sn.ssi.sigmap.service.mapper;


import sn.ssi.sigmap.domain.*;
import sn.ssi.sigmap.service.dto.BailleursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bailleurs} and its DTO {@link BailleursDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BailleursMapper extends EntityMapper<BailleursDTO, Bailleurs> {



    default Bailleurs fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bailleurs bailleurs = new Bailleurs();
        bailleurs.setId(id);
        return bailleurs;
    }
}
