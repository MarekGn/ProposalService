package com.cloudservices.proposalservice.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Getter @Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    @Column(name = "created_date")
    private Date creationDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    private Date lastUpdateDate;

    public AbstractEntity(Long id) {
        this.id = id;
    }
}