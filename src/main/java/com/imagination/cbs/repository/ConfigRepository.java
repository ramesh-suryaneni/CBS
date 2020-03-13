package com.imagination.cbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Config;

@Repository("configRepository")
public interface ConfigRepository extends JpaRepository<Config, Long> {

	List<Config> findBykeyNameStartingWith(String keyName);

	Optional<Config> findByKeyName(String keyName);
	
}
