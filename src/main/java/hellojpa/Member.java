package hellojpa;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class Member {
    @Id
    private Long id;

    @Column(name = "name") // 컬럽네임매칭
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    // private LocalDate testLocalDate;
    // private LocalDateTime testLocalDateTime;

    /*
    @Transient
    private Integer temp;
    */

    @Lob
    private String description;

    public Member() {
    }
}
