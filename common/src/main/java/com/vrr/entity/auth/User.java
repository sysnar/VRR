package com.vrr.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vrr.code.auth.ProviderType;
import com.vrr.code.auth.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VRR_USER")
public class User {

    @JsonIgnore
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SERIAL_NUMBER", length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String serialNumber;

    @Column(name = "EMAIL", length = 100)
    @NotNull
    @Size(max = 100)
    private String email;

    @Column(name = "IS_EMAIL_VERIFIED", length = 1)
    @Size(min = 1, max = 1)
    @NotNull
    private String emailVerified;

    @Column(name = "PASSWORD", length = 128)
    @NotNull
    @Size(max = 128)
    private String password;

    @Column(name = "USERNAME", length = 100)
    @NotNull
    @Size(max = 100)
    private String username;

    @Column(name = "PROFILE_IMAGE_URL", length = 100)
    @NotNull
    @Size(max = 100)
    private String profileImageUrl;

    @Column(name = "PROVIDER_TYPE", length = 20)
    @NotNull
    private ProviderType providerType;

    @Column(name = "ROLE_TYPE", length = 20)
    @NotNull
    private RoleType roleType;

    @Column(name = "CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(
            @NotNull @Size(max = 64) String serialNumber,
            @NotNull @Size(max = 100) String username,
            @NotNull @Size(max = 512) String email,
            @NotNull @Size(max = 1) String isEmailVerified,
            @NotNull @Size(max = 512) String profileImageUrl,
            @NotNull ProviderType providerType,
            @NotNull RoleType roleType,
            @NotNull LocalDateTime createdAt,
            @NotNull LocalDateTime updatedAt
    ) {
        this.serialNumber = serialNumber;
        this.username = username;
        this.password = "NO_PASSWORD";
        this.email = email != null ? email : "NO_EMAIL";
        this.emailVerified = isEmailVerified;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void updateProfile(String username, String profileImageUrl) {
        this.username = username == null ? this.username : username;
        this.profileImageUrl = profileImageUrl == null ? this.profileImageUrl : profileImageUrl;
    }
}
