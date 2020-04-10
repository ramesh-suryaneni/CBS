package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.EmployeePermissions;
import com.imagination.cbs.domain.Permission;

@Repository("employeePermissionsRepository")
public interface EmployeePermissionsRepository extends JpaRepository<EmployeePermissions, Long> {
	EmployeePermissions findByPermission(Permission permission);
}
