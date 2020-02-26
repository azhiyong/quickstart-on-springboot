package xyz.mdou.springboot.jpa.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date birthday;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_dept",
            joinColumns = @JoinColumn(name = "uid", referencedColumnName = "uid"),
            inverseJoinColumns = @JoinColumn(name = "dept_id", referencedColumnName = "id"))
    private List<DepartmentEntity> departments;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @Override
    public String toString() {
        return new StringJoiner(", ", UserEntity.class.getSimpleName() + "[", "]")
                .add("uid=" + uid)
                .add("name='" + name + "'")
                .add("birthday=" + birthday)
                .add("departments=" + Optional.ofNullable(departments).orElse(Collections.emptyList()).stream().map(DepartmentEntity::getId).collect(Collectors.toList()))
                .add("createDate=" + createDate)
                .add("updateDate=" + updateDate)
                .toString();
    }
}
