package com.example.vk.DTO.dialogDto;

import com.example.vk.Entity.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AllDialogGetDto {

    private Long id;

    private String name;

    private String urlToAvatar;

    private Long noReadMessage;

    private MessageDTO lastMessage;

    public AllDialogGetDto(Long id, String name,String urlToAvatar, Long noReadMessage, Message message) {
        this.id = id;
        this.name = name;
        this.urlToAvatar = urlToAvatar;
        this.noReadMessage = noReadMessage;
        this.lastMessage = new MessageDTO(message.getId(), message.getDialogId(), message.getUserId(), message.getText(), message.getTimePost());
    }
}
