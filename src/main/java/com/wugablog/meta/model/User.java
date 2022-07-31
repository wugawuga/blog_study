package com.wugablog.meta.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@DynamicInsert
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreatedDate
    private String createDate;

    private String oauth;

    @PrePersist
    public void onPrePersist(){
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
