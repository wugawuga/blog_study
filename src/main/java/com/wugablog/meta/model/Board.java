package com.wugablog.meta.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob
    private String content; // 섬머노트 라이브러리 사용 <html>사용됨

    private int count;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnoreProperties({"board"})
    @OrderBy("id desc")
    private List<Reply> reply;

    @CreatedDate
    private String createDate;

    @PrePersist
    public void onPrePersist(){
        this.createDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }
}
