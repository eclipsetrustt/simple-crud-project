package com.eclipse.simplecrud.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eclipse.simplecrud.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByOrderByIdAsc(Pageable pageable);


}
