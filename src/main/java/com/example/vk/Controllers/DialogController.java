package com.example.vk.Controllers;


import com.example.vk.DTO.dialogDto.CreateDialogDto;
import com.example.vk.DTO.dialogDto.DialogsDTO;
import com.example.vk.DTO.dialogDto.MessageDTO;
import com.example.vk.DTO.dialogDto.MessageResponseDto;
import com.example.vk.Facade.DialogFacade;
import com.example.vk.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vk")
@Slf4j
@CrossOrigin(origins="http://localhost:3000")
public class DialogController {

    private final DialogFacade dialogFacade;

    @Autowired
    public DialogController(DialogFacade dialogFacade) {
        this.dialogFacade = dialogFacade;
    }

    @GetMapping("/dialogs/user/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getDialogs(@PathVariable("id") Long id){
        if(id == null){
            throw new NotFoundException("Id is null");
        }
        log.info("Get dialog");
        return ResponseEntity.ok(dialogFacade.getAllDialogByUserId(id));
    }

    @PostMapping("/create/dialog")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> addDialogs(@RequestBody CreateDialogDto createDialogDto){
        log.info("Create dialog");
        DialogsDTO dialogsDTO = dialogFacade.createDialog(createDialogDto.getUserOne(), createDialogDto.getUserTwo(), createDialogDto.getName());
        return ResponseEntity.ok(dialogsDTO);
    }

    @PostMapping("/message")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> addMessage(@RequestBody MessageDTO messageDTO){
        log.info("Save message");
        MessageResponseDto message = dialogFacade.saveMessage(messageDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/dialog/{id}")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getDialog(@PathVariable("id") Long id){
        log.info("Get dialog");
        return ResponseEntity.ok(dialogFacade.getDialog(id));
    }
}
