package sn.ssi.sigmap.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BailleursMapperTest {

    private BailleursMapper bailleursMapper;

    @BeforeEach
    public void setUp() {
        bailleursMapper = new BailleursMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(bailleursMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(bailleursMapper.fromId(null)).isNull();
    }
}
