package merliontechs.web.rest;

import merliontechs.TestApp;
import merliontechs.domain.UserWithPerms;
import merliontechs.repository.UserWithPermsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserWithPermsResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserWithPermsResourceIT {

    private static final Long DEFAULT_USER = 1L;
    private static final Long UPDATED_USER = 2L;

    @Autowired
    private UserWithPermsRepository userWithPermsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserWithPermsMockMvc;

    private UserWithPerms userWithPerms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWithPerms createEntity(EntityManager em) {
        UserWithPerms userWithPerms = new UserWithPerms()
            .user(DEFAULT_USER);
        return userWithPerms;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserWithPerms createUpdatedEntity(EntityManager em) {
        UserWithPerms userWithPerms = new UserWithPerms()
            .user(UPDATED_USER);
        return userWithPerms;
    }

    @BeforeEach
    public void initTest() {
        userWithPerms = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserWithPerms() throws Exception {
        int databaseSizeBeforeCreate = userWithPermsRepository.findAll().size();
        // Create the UserWithPerms
        restUserWithPermsMockMvc.perform(post("/api/user-with-perms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWithPerms)))
            .andExpect(status().isCreated());

        // Validate the UserWithPerms in the database
        List<UserWithPerms> userWithPermsList = userWithPermsRepository.findAll();
        assertThat(userWithPermsList).hasSize(databaseSizeBeforeCreate + 1);
        UserWithPerms testUserWithPerms = userWithPermsList.get(userWithPermsList.size() - 1);
        assertThat(testUserWithPerms.getUser()).isEqualTo(DEFAULT_USER);
    }

    @Test
    @Transactional
    public void createUserWithPermsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userWithPermsRepository.findAll().size();

        // Create the UserWithPerms with an existing ID
        userWithPerms.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserWithPermsMockMvc.perform(post("/api/user-with-perms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWithPerms)))
            .andExpect(status().isBadRequest());

        // Validate the UserWithPerms in the database
        List<UserWithPerms> userWithPermsList = userWithPermsRepository.findAll();
        assertThat(userWithPermsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserWithPerms() throws Exception {
        // Initialize the database
        userWithPermsRepository.saveAndFlush(userWithPerms);

        // Get all the userWithPermsList
        restUserWithPermsMockMvc.perform(get("/api/user-with-perms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userWithPerms.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserWithPerms() throws Exception {
        // Initialize the database
        userWithPermsRepository.saveAndFlush(userWithPerms);

        // Get the userWithPerms
        restUserWithPermsMockMvc.perform(get("/api/user-with-perms/{id}", userWithPerms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userWithPerms.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserWithPerms() throws Exception {
        // Get the userWithPerms
        restUserWithPermsMockMvc.perform(get("/api/user-with-perms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserWithPerms() throws Exception {
        // Initialize the database
        userWithPermsRepository.saveAndFlush(userWithPerms);

        int databaseSizeBeforeUpdate = userWithPermsRepository.findAll().size();

        // Update the userWithPerms
        UserWithPerms updatedUserWithPerms = userWithPermsRepository.findById(userWithPerms.getId()).get();
        // Disconnect from session so that the updates on updatedUserWithPerms are not directly saved in db
        em.detach(updatedUserWithPerms);
        updatedUserWithPerms
            .user(UPDATED_USER);

        restUserWithPermsMockMvc.perform(put("/api/user-with-perms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserWithPerms)))
            .andExpect(status().isOk());

        // Validate the UserWithPerms in the database
        List<UserWithPerms> userWithPermsList = userWithPermsRepository.findAll();
        assertThat(userWithPermsList).hasSize(databaseSizeBeforeUpdate);
        UserWithPerms testUserWithPerms = userWithPermsList.get(userWithPermsList.size() - 1);
        assertThat(testUserWithPerms.getUser()).isEqualTo(UPDATED_USER);
    }

    @Test
    @Transactional
    public void updateNonExistingUserWithPerms() throws Exception {
        int databaseSizeBeforeUpdate = userWithPermsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserWithPermsMockMvc.perform(put("/api/user-with-perms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userWithPerms)))
            .andExpect(status().isBadRequest());

        // Validate the UserWithPerms in the database
        List<UserWithPerms> userWithPermsList = userWithPermsRepository.findAll();
        assertThat(userWithPermsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserWithPerms() throws Exception {
        // Initialize the database
        userWithPermsRepository.saveAndFlush(userWithPerms);

        int databaseSizeBeforeDelete = userWithPermsRepository.findAll().size();

        // Delete the userWithPerms
        restUserWithPermsMockMvc.perform(delete("/api/user-with-perms/{id}", userWithPerms.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserWithPerms> userWithPermsList = userWithPermsRepository.findAll();
        assertThat(userWithPermsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
