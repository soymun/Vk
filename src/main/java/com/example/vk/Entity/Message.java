package com.example.vk.Entity;


import com.example.vk.DTO.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    public Message(MessageDTO messageDTO) {
        this.user_id = messageDTO.getUserId();
        this.dialog_id = messageDTO.getDialogId();
        this.text = messageDTO.getTextMessage();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long user_id;

    private Long dialog_id;

    private String text;
}
