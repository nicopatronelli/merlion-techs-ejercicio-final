package merliontechs.web.rest;

import merliontechs.TestApp;
import merliontechs.domain.Permissions;
import merliontechs.repository.PermissionsRepository;

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

import merliontechs.domain.enumeration.Perms;
/**
 * Integration tests for the {@link PermissionsResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PermissionsResourceIT {

    private static final Perms DEFAULT_PERM = Perms.TO_PRODUCT_CREATE_AND_UPDATE;
    private static final Perms UPDATED_PERM = Perms.TO_SHOW_PRODUCT_LIST;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPermissionsMockMvc;

    private Permissions permissions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permissions createEntity(EntityManager em) {
        Permissions permissions = new Permissions()
            .perm(DEFAULT_PERM);
        return permissions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permissions createUpdatedEntity(EntityManager em) {
        Permissions permissions = new Permissions()
            .perm(UPDATED_PERM);
        return permissions;
    }

    @BeforeEach
    public void initTest() {
        permissions = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermissions() throws Exception {
        int databaseSizeBeforeCreate = permissionsRepository.findAll().size();
        // Create the Permissions
        restPermissionsMockMvc.perform(post("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissions)))
            .andExpect(status().isCreated());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeCreate + 1);
        Permissions testPermissions = permissionsList.get(permissionsList.size() - 1);
        assertThat(testPermissions.getPerm()).isEqualTo(DEFAULT_PERM);
    }

    @Test
    @Transactional
    public void createPermissionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permissionsRepository.findAll().size();

        // Create the Permissions with an existing ID
        permissions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermissionsMockMvc.perform(post("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissions)))
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get all the permissionsList
        restPermissionsMockMvc.perform(get("/api/permissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permissions.getId().intValue())))
            .andExpect(jsonPath("$.[*].perm").value(hasItem(DEFAULT_PERM.toString())));
    }
    
    @Test
    @Transactional
    public void getPermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        // Get the permissions
        restPermissionsMockMvc.perform(get("/api/permissions/{id}", permissions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(permissions.getId().intValue()))
            .andExpect(jsonPath("$.perm").value(DEFAULT_PERM.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingPermissions() throws Exception {
        // Get the permissions
        restPermissionsMockMvc.perform(get("/api/permissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();

        // Update the permissions
        Permissions updatedPermissions = permissionsRepository.findById(permissions.getId()).get();
        // Disconnect from session so that the updates on updatedPermissions are not directly saved in db
        em.detach(updatedPermissions);
        updatedPermissions
            .perm(UPDATED_PERM);

        restPermissionsMockMvc.perform(put("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPermissions)))
            .andExpect(status().isOk());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
        Permissions testPermissions = permissionsList.get(permissionsList.size() - 1);
        assertThat(testPermissions.getPerm()).isEqualTo(UPDATED_PERM);
    }

    @Test
    @Transactional
    public void updateNonExistingPermissions() throws Exception {
        int databaseSizeBeforeUpdate = permissionsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermissionsMockMvc.perform(put("/api/permissions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(permissions)))
            .andExpect(status().isBadRequest());

        // Validate the Permissions in the database
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermissions() throws Exception {
        // Initialize the database
        permissionsRepository.saveAndFlush(permissions);

        int databaseSizeBeforeDelete = permissionsRepository.findAll().size();

        // Delete the permissions
        restPermissionsMockMvc.perform(delete("/api/permissions/{id}", permissions.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Permissions> permissionsList = permissionsRepository.findAll();
        assertThat(permissionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
