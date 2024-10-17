package com.ivan.course.entity.user;
import com.ivan.course.entity.Keys;
import com.ivan.course.entity.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    @Column(name = "username")
    protected String username;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "key_id", referencedColumnName = "id")
    protected Keys password;

    @Column(name = "enabled")
    protected boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    protected Collection<Role> roles;

    public User(int id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = new Keys(password);
        this.enabled = enabled;
    }
}
