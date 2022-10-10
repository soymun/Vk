package com.example.vk.Response;

import com.example.vk.DTO.dialogDto.MessageResponseDto;
import com.example.vk.DTO.dialogDto.UserMessageDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class DialogDTOResponse {
    private Long dialogs_id;
    private List<UserMessageDto> userLis;
    private List<MessageResponseDto> messages;
}
