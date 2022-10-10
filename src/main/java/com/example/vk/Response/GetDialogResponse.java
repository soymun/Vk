package com.example.vk.Response;


import com.example.vk.DTO.dialogDto.AllDialogGetDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetDialogResponse {

    private List<AllDialogGetDto> dialogs;
}
