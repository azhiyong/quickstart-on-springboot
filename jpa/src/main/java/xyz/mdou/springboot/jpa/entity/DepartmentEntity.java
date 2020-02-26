package xyz.mdou.springboot.jpa.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "superior", referencedColumnName = "id")
    private DepartmentEntity superior;

    private Integer level;

    @OneToMany(cascade = {CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER,
            mappedBy = "superior")
    private List<DepartmentEntity> children;

    @ManyToMany(mappedBy = "departments")
    private List<UserEntity> users;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;


    @Override
    public String toString() {

        return new StringJoiner(", ", DepartmentEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("superior='" + Optional.ofNullable(superior).map(DepartmentEntity::getId).orElse(null) + "'")
                .add("level=" + level)
                .add("children='" + Optional.ofNullable(children).orElse(Collections.emptyList()).stream().map(DepartmentEntity::getId).collect(Collectors.toList()) + "'")
                .add("createDate=" + createDate)
                .add("updateDate=" + updateDate)
                .toString();
    }
}
