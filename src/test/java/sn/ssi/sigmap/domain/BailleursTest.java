package sn.ssi.sigmap.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import sn.ssi.sigmap.web.rest.TestUtil;

public class BailleursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bailleurs.class);
        Bailleurs bailleurs1 = new Bailleurs();
        bailleurs1.setId(1L);
        Bailleurs bailleurs2 = new Bailleurs();
        bailleurs2.setId(bailleurs1.getId());
        assertThat(bailleurs1).isEqualTo(bailleurs2);
        bailleurs2.setId(2L);
        assertThat(bailleurs1).isNotEqualTo(bailleurs2);
        bailleurs1.setId(null);
        assertThat(bailleurs1).isNotEqualTo(bailleurs2);
    }
}
