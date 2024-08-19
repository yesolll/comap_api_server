package com.yesolll.comap_api_server.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String password;

    @Builder.Default
    private String status = "JOIN_WAITING"; // TODO: ENUM

    public void updateMemberStatusToComplete(String status) {
        this.status = switch (status) {
            case "JOIN_WAITING", "PASSWORD_WAITING" -> "ACTIVE";
            default -> status;
        };
    }
}
