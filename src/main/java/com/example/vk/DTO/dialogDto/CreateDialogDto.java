package com.example.vk.DTO.dialogDto;


import com.example.vk.Entity.TypeDialog;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDialogDto {

    private Long userOne;

    private TypeDialog typeDialog;

    private Long userTwo;

    private String name;
}
