package com.essen.bookstore.respository;

import com.essen.bookstore.model.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
}
