package com.example.vk.DTO.dialogDto;

import com.example.vk.Entity.Dialog;
import com.example.vk.Entity.TypeDialog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class DialogMessageDto {
    private Long id;
    private Long userAdminId;
    private TypeDialog typeDialog;
    private String dialogName;
    private String urlToDialogAvatar;
    private List<MessageDialogDto> messages;

    public DialogMessageDto(Dialog dialog) {
        this.id = dialog.getId();
        this.userAdminId = dialog.getUserAdminId();
        this.typeDialog = dialog.getTypeDialog();
        this.dialogName = dialog.getDialogName();
        this.urlToDialogAvatar = dialog.getUrlToDialogAvatar();
    }
}
