package com.example.vk.Service.Implaye;

import com.example.vk.DTO.dialogDto.*;
import com.example.vk.Entity.*;
import com.example.vk.Entity.Dialog_;
import com.example.vk.Entity.Message_;
import com.example.vk.Entity.NoReadMessage_;
import com.example.vk.Entity.UserDialog_;
import com.example.vk.Entity.User_;
import com.example.vk.Repositories.DialogsRepository;
import com.example.vk.Repositories.UserDialogRepository;
import com.example.vk.Service.DialogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;

@Service
public class DialogsServiceImp implements DialogsService {

    @PersistenceContext
    EntityManager entityManager;

    private final DialogsRepository dialogsRepository;

    private final UserDialogRepository userDialogRepository;
    @Autowired
    public DialogsServiceImp(DialogsRepository dialogsRepository, UserDialogRepository userDialogRepository) {
        this.dialogsRepository = dialogsRepository;
        this.userDialogRepository = userDialogRepository;
    }

    @Transactional
    @Override
    public void save(Dialog dialog) {
        dialogsRepository.save(dialog);
    }

    @Transactional
    @Override
    public void delete(Dialog dialog) {
        dialogsRepository.delete(dialog);
    }

    public List<AllDialogGetDto> getAllById(Long id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<AllDialogGetDto> cq = cb.createQuery(AllDialogGetDto.class);
        Root<UserDialog> root = cq.from(UserDialog.class);
        Join<UserDialog, Dialog> join = root.join(UserDialog_.dialog);

        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<NoReadMessage> root1 = subquery.from(NoReadMessage.class);
        Join<NoReadMessage, Message> join1 = root1.join(NoReadMessage_.MESSAGE);
        subquery.where(cb.equal(join1.get(Message_.dialogId), join.get(Dialog_.id)));
        subquery.select(cb.count(root1.get(NoReadMessage_.messageId)));

        Subquery<Message> subquery1 = cq.subquery(Message.class);
        Root<Message> root2 = subquery1.from(Message.class);

        subquery1.where(cb.and(
                cb.equal(cb.greatest(root2.get(Message_.timePost)), root2.get(Message_.timePost)),
                cb.equal(root2.get(Message_.dialogId), join.get(Dialog_.id))));
        subquery1.select(root2);

        cq.where(cb.equal(root.get(UserDialog_.USER_ID), id));

        cq.multiselect(
                join.get(Dialog_.ID),
                join.get(Dialog_.DIALOG_NAME),
                join.get(Dialog_.urlToDialogAvatar),
                subquery,
                subquery1
        );
        return entityManager.createQuery(cq).getResultList();
    }

    public void saveDialog(CreateDialogDto createDialogDto){
        Dialog dialog = new Dialog();
        dialog.setDialogName(createDialogDto.getName());
        dialog.setTypeDialog(createDialogDto.getTypeDialog());
        Dialog saveDialog = dialogsRepository.save(dialog);

        UserDialog userDialogOne = new UserDialog();
        userDialogOne.setUserId(createDialogDto.getUserOne());
        userDialogOne.setDialogId(saveDialog.getId());
        userDialogRepository.save(userDialogOne);

        if(createDialogDto.getTypeDialog() == TypeDialog.DUO) {
            UserDialog userDialogTwo = new UserDialog();
            userDialogTwo.setUserId(createDialogDto.getUserTwo());
            userDialogTwo.setDialogId(saveDialog.getId());
            userDialogRepository.save(userDialogTwo);
        }
    }

    public List<MessageDialogDto> getMessageByDialogId(Long id){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MessageDialogDto> cq = cb.createQuery(MessageDialogDto.class);
        Root<Message> root = cq.from(Message.class);
        Join<Message, User> join = root.join(Message_.USER);

        cq.where(cb.equal(root.get(Message_.dialogId), id));

        cq.orderBy(cb.desc(root.get(Message_.timePost)));

        cq.multiselect(
                root.get(Message_.id),
                root.get(Message_.dialogId),
                root.get(Message_.userId),
                join.get(User_.name),
                join.get(User_.surname),
                join.get(User_.patronymic),
                join.get(User_.urlToAvatar),
                join.get(User_.link),
                root.get(Message_.text),
                root.get(Message_.timePost)
        );

        return entityManager.createQuery(cq).getResultList();
    }

    public DialogMessageDto getInfoAboutDialog(Long id){
        return new DialogMessageDto(dialogsRepository.findDialogById(id));
    }
}
