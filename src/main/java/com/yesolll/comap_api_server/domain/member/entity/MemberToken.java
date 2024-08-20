package com.yesolll.comap_api_server.domain.member.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberToken {

    @Id
    @Column(name = "TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;

    @Column(nullable = false)
    private Date expirationDate;

    private Long memberId;

    public void refresh(String refreshToken, Date expirationDate) {
        this.refreshToken = refreshToken;
        this.expirationDate = expirationDate;
//        this.expirationDate = LocalDateTime.now().plusHours(1L); // 임시 한시간
    }

}