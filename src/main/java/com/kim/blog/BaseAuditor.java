package com.kim.blog;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 해당 클래스를 상속 받을경우 JPA에서 칼럼으로 인식하도록 함
@EntityListeners(AuditingEntityListener.class) //해당 클래스에 Auditing기능을 포함
public class BaseAuditor {
    @CreatedDate //엔티티의 생성되어 저장될때의 시간
    private LocalDateTime createdTime;
    @LastModifiedDate //조회한 엔티티가 수정될때의 시간
    private LocalDateTime modifiedTime;
}
