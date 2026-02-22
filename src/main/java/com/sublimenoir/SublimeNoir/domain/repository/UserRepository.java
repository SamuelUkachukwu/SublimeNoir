package com.sublimenoir.SublimeNoir.domain.repository;

import com.sublimenoir.SublimeNoir.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    User findByUsername(String username);
}