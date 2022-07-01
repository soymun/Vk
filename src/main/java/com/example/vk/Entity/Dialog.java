package com.example.vk.Entity;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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
    private Long dialogs_id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="userdialogs", joinColumns = @JoinColumn(name = "id"),
                inverseJoinColumns = @JoinColumn(name = "dialogs_id"))
    @ToString.Exclude
    private List<User> userLis;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dialog_id")
    @ToString.Exclude
    private List<Message> messages;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dialog dialog = (Dialog) o;
        return dialogs_id != null && Objects.equals(dialogs_id, dialog.dialogs_id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
