package com.ivan.course.entity.superuser;

import com.ivan.course.entity.user.UserData;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "super_user_data")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class SuperUserData extends UserData {

    public SuperUserData(int id, String firstName, String lastName, LocalDate birthDate) {
        super(id, firstName, lastName, birthDate);
    }

    public SuperUserData(String firstName, String lastName, LocalDate birthDate) {
        super(firstName, lastName, birthDate);
    }
}
