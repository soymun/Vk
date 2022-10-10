package com.example.vk.Service.Implaye;


import com.example.vk.DTO.dialogDto.MessageResponseDto;
import com.example.vk.Entity.Message;
import com.example.vk.Entity.Message_;
import com.example.vk.Repositories.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager entityManager;

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Transactional
    public Message saveMessage(Message message){
        message.setUserId(1L);
        return messageRepository.saveAndFlush(message);
    }

    public List<MessageResponseDto> getMessageByDialogsId(Long dialogId){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MessageResponseDto> cq = cb.createQuery(MessageResponseDto.class);
        Root<Message> root = cq.from(Message.class);

        cq.where(cb.equal(root.get(Message_.dialogId), dialogId));

        cq.multiselect(
                root.get(Message_.USER_ID),
                root.get(Message_.TIME_POST),
                root.get(Message_.TEXT)
        );
        return entityManager.createQuery(cq).getResultList();
    }
}
