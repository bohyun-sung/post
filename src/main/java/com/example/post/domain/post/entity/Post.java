package com.example.post.domain.post.entity;

import com.example.post.domain.common.entity.DefaultTimeStampEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends DefaultTimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", columnDefinition = "VARCHAR(100) NOT NULL COMMENT '제목'")
    private String title;

    @Column(name = "content", columnDefinition = "VARCHAR(500) NOT NULL COMMENT '본문'")
    private String content;

    private Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Post of(String title, String content) {
        return new Post(title, content);
    }

    public void modifyPost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
