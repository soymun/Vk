package com.example.vk.Entity;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
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

    @ToString.Exclude
    private String password;

    private String name;

    private String surname;

    private String about;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @ToString.Exclude
    private List<Post> posts;


    private Role role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="userdialogs", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn(name = "dialogs_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<Dialog> dialogs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
