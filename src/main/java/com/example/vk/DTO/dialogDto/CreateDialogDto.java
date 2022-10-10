package com.example.vk.DTO.dialogDto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateDialogDto {

    private Long userOne;

    private Long userTwo;

    private String name;
}
