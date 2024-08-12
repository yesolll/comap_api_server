package com.yesolll.comap_api_server.domain.user.repository;

import com.yesolll.comap_api_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
