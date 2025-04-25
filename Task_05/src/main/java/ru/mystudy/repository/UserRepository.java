package ru.mystudy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mystudy.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
