package merliontechs.web.rest;

import merliontechs.domain.Permissions;
import merliontechs.repository.PermissionsRepository;
import merliontechs.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link merliontechs.domain.Permissions}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PermissionsResource {

    private final Logger log = LoggerFactory.getLogger(PermissionsResource.class);

    private static final String ENTITY_NAME = "permissions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PermissionsRepository permissionsRepository;

    public PermissionsResource(PermissionsRepository permissionsRepository) {
        this.permissionsRepository = permissionsRepository;
    }

    /**
     * {@code POST  /permissions} : Create a new permissions.
     *
     * @param permissions the permissions to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new permissions, or with status {@code 400 (Bad Request)} if the permissions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/permissions")
    public ResponseEntity<Permissions> createPermissions(@RequestBody Permissions permissions) throws URISyntaxException {
        log.debug("REST request to save Permissions : {}", permissions);
        if (permissions.getId() != null) {
            throw new BadRequestAlertException("A new permissions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Permissions result = permissionsRepository.save(permissions);
        return ResponseEntity.created(new URI("/api/permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /permissions} : Updates an existing permissions.
     *
     * @param permissions the permissions to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated permissions,
     * or with status {@code 400 (Bad Request)} if the permissions is not valid,
     * or with status {@code 500 (Internal Server Error)} if the permissions couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/permissions")
    public ResponseEntity<Permissions> updatePermissions(@RequestBody Permissions permissions) throws URISyntaxException {
        log.debug("REST request to update Permissions : {}", permissions);
        if (permissions.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Permissions result = permissionsRepository.save(permissions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, permissions.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /permissions} : get all the permissions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of permissions in body.
     */
    @GetMapping("/permissions")
    public List<Permissions> getAllPermissions() {
        log.debug("REST request to get all Permissions");
        return permissionsRepository.findAll();
    }

    /**
     * {@code GET  /permissions/:id} : get the "id" permissions.
     *
     * @param id the id of the permissions to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the permissions, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/permissions/{id}")
    public ResponseEntity<Permissions> getPermissions(@PathVariable Long id) {
        log.debug("REST request to get Permissions : {}", id);
        Optional<Permissions> permissions = permissionsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(permissions);
    }

    /**
     * {@code DELETE  /permissions/:id} : delete the "id" permissions.
     *
     * @param id the id of the permissions to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Void> deletePermissions(@PathVariable Long id) {
        log.debug("REST request to delete Permissions : {}", id);
        permissionsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
