package com.pranav.book_network.history;

import com.pranav.book_network.book.Book;
import com.pranav.book_network.common.BaseEntity;
import com.pranav.book_network.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.Column;



@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class BookTransactionHistory extends BaseEntity {

//
//    @Column(name = "user_id")
//    private String userId;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean returned;
    private boolean returnApproved;
}
