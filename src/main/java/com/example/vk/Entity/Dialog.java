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
    private Long dialogs_id;

    @ManyToMany(mappedBy = "dialogs", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<User> userLis= new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "dialog_id")
    @ToString.Exclude
    private List<Message> messages = new ArrayList<>();

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

    public void addUser(User user){
        userLis.add(user);
    }
}
