package merliontechs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import merliontechs.web.rest.TestUtil;

public class UserWithPermsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserWithPerms.class);
        UserWithPerms userWithPerms1 = new UserWithPerms();
        userWithPerms1.setId(1L);
        UserWithPerms userWithPerms2 = new UserWithPerms();
        userWithPerms2.setId(userWithPerms1.getId());
        assertThat(userWithPerms1).isEqualTo(userWithPerms2);
        userWithPerms2.setId(2L);
        assertThat(userWithPerms1).isNotEqualTo(userWithPerms2);
        userWithPerms1.setId(null);
        assertThat(userWithPerms1).isNotEqualTo(userWithPerms2);
    }
}
