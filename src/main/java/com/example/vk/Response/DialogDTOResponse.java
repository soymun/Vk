package com.example.vk.Response;

import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.Dialog;
import com.example.vk.Entity.Message;
import com.example.vk.Entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class DialogDTOResponse {


    public DialogDTOResponse(Dialog dialog){
        this.dialogs_id = dialog.getId();
        dialog.getUserLis().stream().forEach(n -> userLis.add(new UserDTO(n)));
        this.messages = dialog.getMessages();
    }

    private Long dialogs_id;
    private List<UserDTO> userLis = new ArrayList<>();
    private List<Message> messages;
}
