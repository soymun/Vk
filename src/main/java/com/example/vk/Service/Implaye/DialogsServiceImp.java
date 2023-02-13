package com.example.vk.Service.Implaye;

import com.example.vk.DTO.dialogDto.AllDialogGetDto;
import com.example.vk.Entity.*;
import com.example.vk.Entity.Dialog_;
import com.example.vk.Entity.UserDialog_;
import com.example.vk.Repositories.DialogsRepository;
import com.example.vk.Repositories.UserDialogRepository;
import com.example.vk.Service.DialogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
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

        //add last message, count no read message

        cq.where(cb.equal(root.get(UserDialog_.USER_ID), id));

        cq.multiselect(
                join.get(Dialog_.ID),
                join.get(Dialog_.DIALOG_NAME)
        );
        return entityManager.createQuery(cq).getResultList();
    }

    public AllDialogGetDto saveDialog(Long userOne, Long userTwo, String name){
        Dialog dialog = new Dialog();
        dialog.setDialogName(name);
        Dialog saveDialog = dialogsRepository.save(dialog);

        UserDialog userDialogOne = new UserDialog();
        userDialogOne.setUserId(userOne);
        userDialogOne.setDialogId(saveDialog.getId());
        userDialogRepository.save(userDialogOne);

        UserDialog userDialogTwo = new UserDialog();
        userDialogTwo.setUserId(userTwo);
        userDialogTwo.setDialogId(saveDialog.getId());
        userDialogRepository.save(userDialogTwo);

        return null;
    }

    //add create group dialog

}
