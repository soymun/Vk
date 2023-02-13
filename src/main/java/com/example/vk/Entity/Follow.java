package com.example.vk.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_one", insertable = false, updatable = false)
    private User userLinkOne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_two", insertable = false, updatable = false)
    private User userLinkTwo;

    @Column(name = "user_one")
    private Long userOne;

    @Column(name = "user_two")
    private Long userTwo;

    public Follow(Long userOne, Long userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
    }
}
