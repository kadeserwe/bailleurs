package sn.ssi.sigmap.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sn.ssi.sigmap.web.rest.TestUtil;

public class BailleursDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BailleursDTO.class);
        BailleursDTO bailleursDTO1 = new BailleursDTO();
        bailleursDTO1.setId(1L);
        BailleursDTO bailleursDTO2 = new BailleursDTO();
        assertThat(bailleursDTO1).isNotEqualTo(bailleursDTO2);
        bailleursDTO2.setId(bailleursDTO1.getId());
        assertThat(bailleursDTO1).isEqualTo(bailleursDTO2);
        bailleursDTO2.setId(2L);
        assertThat(bailleursDTO1).isNotEqualTo(bailleursDTO2);
        bailleursDTO1.setId(null);
        assertThat(bailleursDTO1).isNotEqualTo(bailleursDTO2);
    }
}
