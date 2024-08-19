package com.yesolll.comap_api_server.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String email;

    private String authKey;

    private LocalDateTime expireDate;

    private String authType; // TODO: ENUM

    public void updateAuthTypeToComplete(String authType) {
        this.authType = switch (authType) {
            case "JOIN_WAITING" -> "JOIN_COMPLETE";
            case "PASSWORD_WAITING" -> "PASSWORD_COMPLETE";
            default -> authType;
        };
    }
}
