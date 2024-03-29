package com.example.vk.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    private User user;


    @Column(name ="dialog_id")
    private Long dialogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id",  insertable = false, updatable = false)
    private Dialog dialog;

    @JsonFormat(pattern = "yyyy-mm-dd hh:mm")
    private LocalDate timePost;

    private String text;
}
