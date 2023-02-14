package com.example.vk.Facade;


import com.example.vk.DTO.dialogDto.*;
import com.example.vk.Entity.Message;
import com.example.vk.Response.DialogDTOResponse;
import com.example.vk.Response.GetDialogResponse;
import com.example.vk.Service.Implaye.DialogsServiceImp;
import com.example.vk.Service.Implaye.MessageService;
import com.example.vk.Service.Implaye.UserServiceImp;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class DialogFacade {

    private final UserServiceImp userServiceImp;
    private final DialogsServiceImp dialogsServiceImp;

    private final MessageService messageService;

    @Autowired
    public DialogFacade(UserServiceImp userServiceImp, DialogsServiceImp dialogsServiceImp, MessageService messageService) {
        this.userServiceImp = userServiceImp;
        this.dialogsServiceImp = dialogsServiceImp;
        this.messageService = messageService;
    }

    public GetDialogResponse getAllDialogByUserId(Long userId){
        log.info("Get dialog by user id {}", userId);
        if(userId == null){
            throw  new NotFoundException("Not found dialogs");
        }
        List<AllDialogGetDto> dialog = dialogsServiceImp.getAllById(userId);
        log.info("Dialogs found");
        return GetDialogResponse.builder().dialogs(dialog).build();
    }

    public DialogsDTO createDialog(Long userOne, Long userTwo, String name){
        log.info("Create dialog with name {}", name);
        AllDialogGetDto allDialogGetDto = dialogsServiceImp.saveDialog(userOne, userTwo, name);
        log.info("Dialog created");
        return new DialogsDTO(allDialogGetDto.getId(), allDialogGetDto.getName());
    }

    public MessageResponseDto saveMessage(MessageDTO messageDTO){
        log.info("Save message {}", messageDTO);
        Message message = new Message();
        message.setDialogId(messageDTO.getDialogId());
        message.setUserId(messageDTO.getUserId());
        message.setText(messageDTO.getTextMessage());
        message.setTimePost(LocalDate.now());
        Message messageSaved = messageService.saveMessage(message);
        log.info("Message saved {}", messageSaved);
        return null;
    }

    public DialogDTOResponse getDialog(Long dialogId){
        log.info("Get dialog by dialog id {}", dialogId);
        List<UserMessageDto> users = userServiceImp.getUserByDialogId(dialogId);
        List<MessageResponseDto> messages = messageService.getMessageByDialogsId(dialogId);
        return DialogDTOResponse.builder().dialogs_id(dialogId).messages(messages).userLis(users).build();
    }
}
