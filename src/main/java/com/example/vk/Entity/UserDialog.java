package com.example.vk.Entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@IdClass(UserDialogKey.class)
public class UserDialog {


    @Id
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",  insertable = false, updatable = false)
    private User user;

    @Id
    @Column(name = "dialog_id")
    private Long dialogId;

    @ManyToOne
    @JoinColumn(name = "dialog_id", referencedColumnName = "id",  insertable = false, updatable = false)
    private Dialog dialog;
}
