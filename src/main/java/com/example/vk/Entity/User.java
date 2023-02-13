package com.example.vk.Entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Role role;

    private String name;

    @Column(name = "url_to_avatar")
    private String urlToAvatar;

    private String surname;

    private String patronymic;

    private String about;

    private LocalDate birthday;

    private String link;

}
