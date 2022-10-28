package com.insomnia_studio.w4156pj.repository;

import com.insomnia_studio.w4156pj.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByUserId(String userId);

    Integer deleteUserEntityByUserId(String userId);
}
