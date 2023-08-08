package com.example.post.domain.common.entity;

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
@Table(name = "address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(name = "zipcode", columnDefinition = "VARCHAR(20) COMMENT '우편번호'")
    private String zipcode;

    @Column(name = "address1", columnDefinition = "VARCHAR(100) COMMENT '주소1'")
    private String address1;

    @Column(name = "address2", columnDefinition = "VARCHAR(100) COMMENT '주소2'")
    private String address2;
}
