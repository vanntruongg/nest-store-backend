package vantruong.nststore.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

  @CreatedDate
  @JsonIgnore
  @Column(name = "created_date", nullable = false, updatable = false)
  private LocalDateTime createdDate = LocalDateTime.now();

  @LastModifiedDate
  @JsonIgnore
  @Column(name = "modified_date", nullable = false)
  private LocalDateTime modifiedDate = LocalDateTime.now();
}
