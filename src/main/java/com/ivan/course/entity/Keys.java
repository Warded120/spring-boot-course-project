package com.ivan.course.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "keys")
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
