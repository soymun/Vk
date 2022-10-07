package com.example.vk.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userOne;

    private Long userTwo;

    public Follow(Long userOne, Long userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
    }
}
