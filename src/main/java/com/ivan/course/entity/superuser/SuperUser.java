package com.ivan.course.entity.superuser;

import com.ivan.course.dto.usersDto.SuperUserDto;
import com.ivan.course.entity.Keys;
import com.ivan.course.entity.Role;
import com.ivan.course.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "super_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SuperUser extends User {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "super_user_data_id", referencedColumnName = "id")
    SuperUserData superUserData;

    public SuperUser(SuperUserDto superUserDto) {
        super(superUserDto.getId(), superUserDto.getUsername(), superUserDto.getPassword(), superUserDto.isEnabled());
        this.roles = getRolesFromTemplate(superUserDto.getTopRole());
        superUserData = new SuperUserData(superUserDto.getId(), superUserDto.getFirstName(), superUserDto.getLastName(), superUserDto.getBirthDate());
    }

    public SuperUser(String username, Keys password, boolean enabled, Collection<Role> roles, SuperUserData superUserData) {
        super(username, password, enabled, roles);
        this.superUserData = superUserData;
    }

    private List<Role> getRolesFromTemplate(String topRole) {
        List<Role> tempAuthorities = new ArrayList<>(List.of(new Role("ROLE_OPERATOR")));

        if (topRole.equals("admin")) {
            tempAuthorities.add(new Role("ROLE_ADMIN"));
        }
        return tempAuthorities;
    }
}
