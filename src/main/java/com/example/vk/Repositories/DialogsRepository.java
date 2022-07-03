package com.example.vk.Repositories;

import com.example.vk.Entity.Dialog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogsRepository extends JpaRepository<Dialog, Long> {
}
