package com.ivan.course.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "keys")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Keys {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "password")
    private String password;

    public Keys(String password) {
        this.password = password;
    }
}
