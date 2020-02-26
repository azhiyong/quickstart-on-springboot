package xyz.mdou.springboot.jpa.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mdou.springboot.jpa.JpaApplicationTest;
import xyz.mdou.springboot.jpa.entity.DepartmentEntity;
import xyz.mdou.springboot.jpa.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DepartmentRepositoryTest extends JpaApplicationTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSave() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findByLevel(0);
        if (departmentEntities.size() == 0) {
            DepartmentEntity testSave1 = new DepartmentEntity().setName("testSave1").setLevel(0).setSuperior(null);
            DepartmentEntity testSave11 = new DepartmentEntity().setName("testSave1_1").setLevel(1).setSuperior(testSave1);
            DepartmentEntity testSave12 = new DepartmentEntity().setName("testSave1_2").setLevel(1).setSuperior(testSave1);
            DepartmentEntity testSave111 = new DepartmentEntity().setName("testSave1_1_1").setLevel(2).setSuperior(testSave11);
            departmentEntities.add(testSave1);
            departmentEntities.add(testSave11);
            departmentEntities.add(testSave12);
            departmentEntities.add(testSave111);
            departmentRepository.saveAll(departmentEntities);

            Collection<DepartmentEntity> deptall = departmentRepository.findAll();
            log.info("all department: {}", deptall);
        }

        userRepository.findById(1L).ifPresent(user -> {
            user.setName("添加部门");
            DepartmentEntity dept = departmentRepository.findById(2L).orElse(null);
            user.setDepartments(Collections.singletonList(dept));
            userRepository.save(user);
        });
        log.info("用户部门={}", userRepository.findById(1L).map(UserEntity::getDepartments).orElse(null));

        userRepository.findById(1L).ifPresent(user -> {
            user.setName("清空部门");
            user.setDepartments(null);
            userRepository.save(user);
        });
        log.info("用户部门={}", userRepository.findById(1L).map(UserEntity::getDepartments));
    }
}