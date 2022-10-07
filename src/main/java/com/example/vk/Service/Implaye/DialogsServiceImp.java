package com.example.vk.Service.Implaye;

import com.example.vk.Entity.Dialog;
import com.example.vk.Repositories.DialogsRepository;
import com.example.vk.Service.DialogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DialogsServiceImp implements DialogsService {

    private final DialogsRepository dialogsRepository;
    @Autowired
    public DialogsServiceImp(DialogsRepository dialogsRepository) {
        this.dialogsRepository = dialogsRepository;
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

    @Transactional
    public Dialog findDialogById(Long id){
        return dialogsRepository.findDialogById(id);
    }
}
