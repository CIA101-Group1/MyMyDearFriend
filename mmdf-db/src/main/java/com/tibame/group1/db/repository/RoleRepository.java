package com.tibame.group1.db.repository;

import com.tibame.group1.db.entity.RoleEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

}
