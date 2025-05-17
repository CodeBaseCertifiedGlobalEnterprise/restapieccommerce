package com.sampleProject.sampleProjectRestApi.Authentication;

import com.sampleProject.sampleProjectRestApi.user.User;
import jakarta.persistence.*;

import java.util.Date;

import lombok.Data;

@Data
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Date expiryDate;

    public RefreshToken(){

    }
}
