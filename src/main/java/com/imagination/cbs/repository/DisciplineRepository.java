package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imagination.cbs.domain.Discipline;

@Repository("disciplineRepository")
public interface DisciplineRepository extends JpaRepository<Discipline, Long> {

}
