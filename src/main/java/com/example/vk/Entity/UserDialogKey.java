package com.example.vk.Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class UserDialogKey implements Serializable {

    @Column(name = "dialog_id")
    private Long dialogId;

    @Column(name = "user_id")
    private Long userId;
}
