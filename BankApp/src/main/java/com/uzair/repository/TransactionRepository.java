package com.uzair.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uzair.entity.Transaction;
import com.uzair.entity.Users;
import java.util.List;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findBySenderUserOrReceiverUser(Users senderUser, Users receiverUser);
}
