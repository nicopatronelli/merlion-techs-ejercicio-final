package merliontechs.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import merliontechs.domain.enumeration.Perms;

/**
 * A Permissions.
 */
@Entity
@Table(name = "permissions")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "perm")
    private Perms perm;

    @ManyToOne
    @JsonIgnoreProperties(value = "users", allowSetters = true)
    private UserWithPerms userWithPerms;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Perms getPerm() {
        return perm;
    }

    public Permissions perm(Perms perm) {
        this.perm = perm;
        return this;
    }

    public void setPerm(Perms perm) {
        this.perm = perm;
    }

    public UserWithPerms getUserWithPerms() {
        return userWithPerms;
    }

    public Permissions userWithPerms(UserWithPerms userWithPerms) {
        this.userWithPerms = userWithPerms;
        return this;
    }

    public void setUserWithPerms(UserWithPerms userWithPerms) {
        this.userWithPerms = userWithPerms;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permissions)) {
            return false;
        }
        return id != null && id.equals(((Permissions) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Permissions{" +
            "id=" + getId() +
            ", perm='" + getPerm() + "'" +
            "}";
    }
}
