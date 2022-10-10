package com.example.vk.Repositories;

import com.example.vk.Entity.UserDialog;
import com.example.vk.Entity.UserDialogKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDialogRepository extends JpaRepository<UserDialog, UserDialogKey> {
}
