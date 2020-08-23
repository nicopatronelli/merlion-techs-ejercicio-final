package merliontechs.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UserWithPerms.
 */
@Entity
@Table(name = "user_with_perms")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserWithPerms implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "jhi_user")
    private Long user;

    @OneToMany(mappedBy = "userWithPerms")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Permissions> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser() {
        return user;
    }

    public UserWithPerms user(Long user) {
        this.user = user;
        return this;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Set<Permissions> getUsers() {
        return users;
    }

    public UserWithPerms users(Set<Permissions> permissions) {
        this.users = permissions;
        return this;
    }

    public UserWithPerms addUser(Permissions permissions) {
        this.users.add(permissions);
        permissions.setUserWithPerms(this);
        return this;
    }

    public UserWithPerms removeUser(Permissions permissions) {
        this.users.remove(permissions);
        permissions.setUserWithPerms(null);
        return this;
    }

    public void setUsers(Set<Permissions> permissions) {
        this.users = permissions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserWithPerms)) {
            return false;
        }
        return id != null && id.equals(((UserWithPerms) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserWithPerms{" +
            "id=" + getId() +
            ", user=" + getUser() +
            "}";
    }
}
