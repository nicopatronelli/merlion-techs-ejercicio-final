package merliontechs.repository;

import merliontechs.TestApp;
import merliontechs.config.Constants;
import merliontechs.config.audit.AuditEventConverter;
import merliontechs.domain.Permissions;
import merliontechs.domain.PersistentAuditEvent;
import merliontechs.domain.User;
import merliontechs.domain.UserWithPerms;
import merliontechs.domain.enumeration.Perms;
import org.apache.commons.lang3.RandomStringUtils;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static merliontechs.repository.CustomAuditEventRepository.EVENT_DATA_COLUMN_MAX_LENGTH;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = TestApp.class)
@Transactional
@DisplayName("Tests of Product Repository")
public class GetPermissionsOfAnUserTests {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserWithPermsRepository userWithPermsRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Test
    public void test() {
        User user = getAnUser();
        UserWithPerms userWithPermsSaved = getUserWithPerms(user);

        // Add a permission TO_SHOW_PRODUCT_LIST
        Permissions permissionToShowList = new Permissions();
        permissionToShowList.setUserWithPerms(userWithPermsSaved);
        permissionToShowList.setPerm(Perms.TO_SHOW_PRODUCT_LIST);
        permissionsRepository.save(permissionToShowList);

        // Add a permission TO_PRODUCT_DETAIL
        Permissions permissionToProductDetail = new Permissions();
        permissionToProductDetail.setUserWithPerms(userWithPermsSaved);
        permissionToProductDetail.setPerm(Perms.TO_PRODUCT_DETAIL);
        permissionsRepository.save(permissionToProductDetail);

        List<String> permissions = userWithPermsRepository.findUserPermissionsByUserName(user.getLogin());
        assertThat(permissions).contains(Perms.TO_SHOW_PRODUCT_LIST.name());
        assertThat(permissions).contains(Perms.TO_PRODUCT_DETAIL.name());
        assertThat(permissions).doesNotContain(Perms.TO_PRODUCT_DELETE.name());
    }
    private User getAnUser() {
        user = new User();
        user.setLogin("merlionTechs");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("merlionTechs@gmail.com");
        userRepository.save(user);
        Optional<User> userSaved = userRepository.findOneByLogin(user.getLogin());
        return userSaved.get();
    }

    private UserWithPerms getUserWithPerms(User user) {
        UserWithPerms userWithPerms = new UserWithPerms();
        userWithPerms.setUser(user.getId());
        userWithPermsRepository.save(userWithPerms);
        return userWithPermsRepository.findAll().get(0);
    }


//    @BeforeEach
//    public void setup() {
//        customAuditEventRepository = new CustomAuditEventRepository(persistenceAuditEventRepository, auditEventConverter);
//        persistenceAuditEventRepository.deleteAll();
//        Instant oneHourAgo = Instant.now().minusSeconds(3600);
//
//        PersistentAuditEvent testUserEvent = new PersistentAuditEvent();
//        testUserEvent.setPrincipal("test-user");
//        testUserEvent.setAuditEventType("test-type");
//        testUserEvent.setAuditEventDate(oneHourAgo);
//        Map<String, String> data = new HashMap<>();
//        data.put("test-key", "test-value");
//        testUserEvent.setData(data);
//
//        PersistentAuditEvent testOldUserEvent = new PersistentAuditEvent();
//        testOldUserEvent.setPrincipal("test-user");
//        testOldUserEvent.setAuditEventType("test-type");
//        testOldUserEvent.setAuditEventDate(oneHourAgo.minusSeconds(10000));
//
//        PersistentAuditEvent testOtherUserEvent = new PersistentAuditEvent();
//        testOtherUserEvent.setPrincipal("other-test-user");
//        testOtherUserEvent.setAuditEventType("test-type");
//        testOtherUserEvent.setAuditEventDate(oneHourAgo);
//    }
//
//    @Test
//    public void addAuditEvent() {
//        Map<String, Object> data = new HashMap<>();
//        data.put("test-key", "test-value");
//        AuditEvent event = new AuditEvent("test-user", "test-type", data);
//        customAuditEventRepository.add(event);
//        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
//        assertThat(persistentAuditEvents).hasSize(1);
//        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
//        assertThat(persistentAuditEvent.getPrincipal()).isEqualTo(event.getPrincipal());
//        assertThat(persistentAuditEvent.getAuditEventType()).isEqualTo(event.getType());
//        assertThat(persistentAuditEvent.getData()).containsKey("test-key");
//        assertThat(persistentAuditEvent.getData().get("test-key")).isEqualTo("test-value");
//        assertThat(persistentAuditEvent.getAuditEventDate().truncatedTo(ChronoUnit.MILLIS))
//            .isEqualTo(event.getTimestamp().truncatedTo(ChronoUnit.MILLIS));
//    }
//
//    @Test
//    public void addAuditEventTruncateLargeData() {
//        Map<String, Object> data = new HashMap<>();
//        StringBuilder largeData = new StringBuilder();
//        for (int i = 0; i < EVENT_DATA_COLUMN_MAX_LENGTH + 10; i++) {
//            largeData.append("a");
//        }
//        data.put("test-key", largeData);
//        AuditEvent event = new AuditEvent("test-user", "test-type", data);
//        customAuditEventRepository.add(event);
//        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
//        assertThat(persistentAuditEvents).hasSize(1);
//        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
//        assertThat(persistentAuditEvent.getPrincipal()).isEqualTo(event.getPrincipal());
//        assertThat(persistentAuditEvent.getAuditEventType()).isEqualTo(event.getType());
//        assertThat(persistentAuditEvent.getData()).containsKey("test-key");
//        String actualData = persistentAuditEvent.getData().get("test-key");
//        assertThat(actualData.length()).isEqualTo(EVENT_DATA_COLUMN_MAX_LENGTH);
//        assertThat(actualData).isSubstringOf(largeData);
//        assertThat(persistentAuditEvent.getAuditEventDate().truncatedTo(ChronoUnit.MILLIS))
//            .isEqualTo(event.getTimestamp().truncatedTo(ChronoUnit.MILLIS));
//    }
//
//    @Test
//    public void testAddEventWithWebAuthenticationDetails() {
//        HttpSession session = new MockHttpSession(null, "test-session-id");
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setSession(session);
//        request.setRemoteAddr("1.2.3.4");
//        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
//        Map<String, Object> data = new HashMap<>();
//        data.put("test-key", details);
//        AuditEvent event = new AuditEvent("test-user", "test-type", data);
//        customAuditEventRepository.add(event);
//        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
//        assertThat(persistentAuditEvents).hasSize(1);
//        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
//        assertThat(persistentAuditEvent.getData().get("remoteAddress")).isEqualTo("1.2.3.4");
//        assertThat(persistentAuditEvent.getData().get("sessionId")).isEqualTo("test-session-id");
//    }
//
//    @Test
//    public void testAddEventWithNullData() {
//        Map<String, Object> data = new HashMap<>();
//        data.put("test-key", null);
//        AuditEvent event = new AuditEvent("test-user", "test-type", data);
//        customAuditEventRepository.add(event);
//        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
//        assertThat(persistentAuditEvents).hasSize(1);
//        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
//        assertThat(persistentAuditEvent.getData().get("test-key")).isEqualTo("null");
//    }
//
//    @Test
//    public void addAuditEventWithAnonymousUser() {
//        Map<String, Object> data = new HashMap<>();
//        data.put("test-key", "test-value");
//        AuditEvent event = new AuditEvent(Constants.ANONYMOUS_USER, "test-type", data);
//        customAuditEventRepository.add(event);
//        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
//        assertThat(persistentAuditEvents).hasSize(0);
//    }
//
//    @Test
//    public void addAuditEventWithAuthorizationFailureType() {
//        Map<String, Object> data = new HashMap<>();
//        data.put("test-key", "test-value");
//        AuditEvent event = new AuditEvent("test-user", "AUTHORIZATION_FAILURE", data);
//        customAuditEventRepository.add(event);
//        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
//        assertThat(persistentAuditEvents).hasSize(0);
//    }

}
