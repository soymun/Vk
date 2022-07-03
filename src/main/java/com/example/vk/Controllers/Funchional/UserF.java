package com.example.vk.Controllers.Funchional;

import com.example.vk.DTO.UserDTO;
import com.example.vk.Entity.Dialog;
import com.example.vk.Entity.User;

public class UserF {

    public static Dialog saveDialog(User userOne, User userTwo){
        if(userOne == null || userTwo == null){
            throw new RuntimeException("No find user");
        }
        Dialog dialog = new Dialog();
        dialog.addUser(userOne);
        dialog.addUser(userTwo);
        userOne.addDialog(dialog);
        userTwo.addDialog(dialog);
        return dialog;
    }
    public static User save(User user, UserDTO userDTO){
        if(user == null || userDTO == null){
            throw new RuntimeException("Exception with update User");
        }
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setAbout(userDTO.getAbout());
        user.setPosts(userDTO.getPosts());
        return user;
    }
}
