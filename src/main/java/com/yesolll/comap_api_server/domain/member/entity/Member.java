package com.yesolll.comap_api_server.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String password;

    @Builder.Default
    private String status = "JOIN_WAITING"; // TODO: ENUM

    public void encryptPassword(String encPassword) {
        this.password = encPassword;
    }

    public void updateMemberStatusToComplete(String status) {
        this.status = switch (status) {
            case "JOIN_WAITING", "PASSWORD_WAITING" -> "ACTIVE";
            default -> status;
        };
    }
}
