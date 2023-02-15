package com.example.vk.Controllers;


import com.example.vk.DTO.dialogDto.*;
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

    @GetMapping("/dialogs/user")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getDialogs(@RequestParam("id") Long id){
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
        return dialogFacade.createDialog(createDialogDto);
    }

    @PostMapping("/message")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> addMessage(@RequestBody MessageCreateDto messageDTO){
        log.info("Save message");
        return dialogFacade.saveMessage(messageDTO);
    }

    @GetMapping("/message")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getMessage(@RequestParam("id") Long id){
        log.info("Get dialog");
        return ResponseEntity.ok(dialogFacade.getDialog(id));
    }

    @GetMapping("/dialog")
    @PreAuthorize(value = "hasAuthority('USER')")
    public ResponseEntity<?> getDialog(@RequestParam("id") Long id){
        log.info("Get dialog");
        return ResponseEntity.ok(dialogFacade.getInfAboutDialog(id));
    }
}
