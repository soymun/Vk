package com.example.vk.Facade;


import com.example.vk.DTO.dialogDto.AllDialogGetDto;
import com.example.vk.DTO.dialogDto.CreateDialogDto;
import com.example.vk.DTO.dialogDto.MessageCreateDto;
import com.example.vk.Entity.Message;
import com.example.vk.Response.ResponseDto;
import com.example.vk.Service.Implaye.DialogsServiceImp;
import com.example.vk.Service.Implaye.MessageService;
import com.example.vk.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DialogFacade {

    private final DialogsServiceImp dialogsServiceImp;

    private final MessageService messageService;

    public ResponseEntity<?> getAllDialogByUserId(Long userId){
        log.info("Get dialog by user id {}", userId);
        if(userId == null){
            throw  new NotFoundException("Not found dialogs");
        }
        List<AllDialogGetDto> dialog = dialogsServiceImp.getAllById(userId);
        log.info("Dialogs found");
        return ResponseEntity.ok(ResponseDto.builder().data(dialog).build());
    }

    public ResponseEntity<?> createDialog(CreateDialogDto createDialogDto){
        dialogsServiceImp.saveDialog(createDialogDto);
        log.info("Dialog created");
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> saveMessage(MessageCreateDto messageDTO){
        log.info("Save message {}", messageDTO);
        Message message = new Message();
        message.setDialogId(messageDTO.getDialogId());
        message.setUserId(messageDTO.getUserId());
        message.setText(messageDTO.getTextMessage());
        message.setTimePost(LocalDate.now());
        Message messageSaved = messageService.saveMessage(message);
        log.info("Message saved {}", messageSaved);
        return ResponseEntity.status(201).build();
    }

    public ResponseEntity<?> getDialog(Long dialogId){
        log.info("Get message in dialog id {}", dialogId);
        return ResponseEntity.ok(ResponseDto.builder().data(dialogsServiceImp.getMessageByDialogId(dialogId)).build());
    }

    public ResponseEntity<?> getInfAboutDialog(Long dialogId){
        log.info("Get inf about dialog with id {}" , dialogId);
        return ResponseEntity.ok(ResponseDto.builder().data(dialogsServiceImp.getInfoAboutDialog(dialogId)).build());
    }
}
