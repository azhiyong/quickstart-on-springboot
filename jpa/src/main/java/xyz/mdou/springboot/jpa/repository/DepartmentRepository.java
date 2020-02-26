package xyz.mdou.springboot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.mdou.springboot.jpa.entity.DepartmentEntity;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {

    @Query("select e from DepartmentEntity e where e.level=?1")
    List<DepartmentEntity> findByLevel(Integer level);
}
