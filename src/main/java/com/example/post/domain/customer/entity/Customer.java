package com.example.post.domain.customer.entity;

import com.example.post.domain.common.entity.Address;
import com.example.post.domain.common.entity.DefaultTimeStampEntity;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Customer extends DefaultTimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50) COMMENT '이름'")
    private String name;

    @Column(name = "email", columnDefinition = "VARCHAR(100) COMMENT '이메일'")
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(100) COMMENT '비밀번호'")
    private String password;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", columnDefinition = "BIGINT UNSIGNED COMMENT '주소'")
    private Address address;

}
