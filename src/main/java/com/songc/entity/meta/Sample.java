package com.songc.entity.meta;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created By @author songc
 * on 2017/12/4
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Sample {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    private String growth;
    private String period;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    @Column(nullable = false)
    private Long userId;
}
