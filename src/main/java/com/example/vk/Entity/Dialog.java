package com.example.vk.Entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dialogName;

    private TypeDialog typeDialog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_admin_id", insertable = false, updatable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "user_admin_id")
    private Long userAdminId;

    private String urlToDialogAvatar;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dialog dialog = (Dialog) o;
        return id != null && Objects.equals(id, dialog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
