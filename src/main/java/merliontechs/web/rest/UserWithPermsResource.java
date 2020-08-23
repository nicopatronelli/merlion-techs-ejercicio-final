package merliontechs.web.rest;

import merliontechs.domain.UserWithPerms;
import merliontechs.repository.UserWithPermsRepository;
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
 * REST controller for managing {@link merliontechs.domain.UserWithPerms}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserWithPermsResource {

    private final Logger log = LoggerFactory.getLogger(UserWithPermsResource.class);

    private static final String ENTITY_NAME = "userWithPerms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserWithPermsRepository userWithPermsRepository;

    public UserWithPermsResource(UserWithPermsRepository userWithPermsRepository) {
        this.userWithPermsRepository = userWithPermsRepository;
    }

    /**
     * {@code POST  /user-with-perms} : Create a new userWithPerms.
     *
     * @param userWithPerms the userWithPerms to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userWithPerms, or with status {@code 400 (Bad Request)} if the userWithPerms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-with-perms")
    public ResponseEntity<UserWithPerms> createUserWithPerms(@RequestBody UserWithPerms userWithPerms) throws URISyntaxException {
        log.debug("REST request to save UserWithPerms : {}", userWithPerms);
        if (userWithPerms.getId() != null) {
            throw new BadRequestAlertException("A new userWithPerms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserWithPerms result = userWithPermsRepository.save(userWithPerms);
        return ResponseEntity.created(new URI("/api/user-with-perms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-with-perms} : Updates an existing userWithPerms.
     *
     * @param userWithPerms the userWithPerms to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userWithPerms,
     * or with status {@code 400 (Bad Request)} if the userWithPerms is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userWithPerms couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-with-perms")
    public ResponseEntity<UserWithPerms> updateUserWithPerms(@RequestBody UserWithPerms userWithPerms) throws URISyntaxException {
        log.debug("REST request to update UserWithPerms : {}", userWithPerms);
        if (userWithPerms.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserWithPerms result = userWithPermsRepository.save(userWithPerms);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userWithPerms.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-with-perms} : get all the userWithPerms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userWithPerms in body.
     */
    @GetMapping("/user-with-perms")
    public List<UserWithPerms> getAllUserWithPerms() {
        log.debug("REST request to get all UserWithPerms");
        return userWithPermsRepository.findAll();
    }

    /**
     * {@code GET  /user-with-perms/:id} : get the "id" userWithPerms.
     *
     * @param id the id of the userWithPerms to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userWithPerms, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-with-perms/{id}")
    public ResponseEntity<UserWithPerms> getUserWithPerms(@PathVariable Long id) {
        log.debug("REST request to get UserWithPerms : {}", id);
        Optional<UserWithPerms> userWithPerms = userWithPermsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userWithPerms);
    }

    /**
     * {@code DELETE  /user-with-perms/:id} : delete the "id" userWithPerms.
     *
     * @param id the id of the userWithPerms to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-with-perms/{id}")
    public ResponseEntity<Void> deleteUserWithPerms(@PathVariable Long id) {
        log.debug("REST request to delete UserWithPerms : {}", id);
        userWithPermsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
