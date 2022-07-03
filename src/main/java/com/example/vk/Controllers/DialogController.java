package com.example.vk.Controllers;


import com.example.vk.Controllers.Funchional.UserF;
import com.example.vk.DTO.DialogsDTO;
import com.example.vk.DTO.Response.DialogDTOResponse;
import com.example.vk.Entity.Dialog;
import com.example.vk.Entity.User;
import com.example.vk.Service.Implaye.DialogsServiceImp;
import com.example.vk.Service.Implaye.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vk")
@Slf4j
public class DialogController {

    private final UserServiceImp userServiceImp;
    private final DialogsServiceImp dialogsServiceImp;
    @Autowired
    public DialogController(UserServiceImp userServiceImp, DialogsServiceImp dialogsServiceImp) {
        this.userServiceImp = userServiceImp;
        this.dialogsServiceImp = dialogsServiceImp;
    }

    @GetMapping("/dialogs/{id}")
    public ResponseEntity<List<DialogDTOResponse>> getDialogs(@PathVariable("id") Long id){
        if(id == null){
            throw new RuntimeException("Id is null");
        }
        User user = userServiceImp.getUserById(id);
        List<DialogDTOResponse> dialogDTOResponses = new ArrayList<>();
        user.getDialogs().stream().forEach(n -> dialogDTOResponses.add(new DialogDTOResponse(n)));
        return ResponseEntity.ok(dialogDTOResponses);
    }

    @PostMapping("/dialogs/{id}")
    public ResponseEntity<?> addDialogs(@PathVariable("id") Long id, @RequestBody DialogsDTO dialogsDTO){
        User userOne = userServiceImp.getUserById(id);
        User userTwo = userServiceImp.getUserById(dialogsDTO.getUserId());
        Dialog dialog = UserF.saveDialog(userOne, userTwo);
        dialogsServiceImp.save(dialog);
        userServiceImp.save(userOne);
        userServiceImp.save(userTwo);
        return ResponseEntity.ok("Suggest");
    }
}
