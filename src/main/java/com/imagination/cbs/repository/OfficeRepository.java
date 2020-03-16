package com.imagination.cbs.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.imagination.cbs.domain.OfficeDm;

@Repository("officeRepository")
public interface OfficeRepository extends CrudRepository<OfficeDm, Long> {
	List<OfficeDm> findByOfficeId(Long officeId);
}
