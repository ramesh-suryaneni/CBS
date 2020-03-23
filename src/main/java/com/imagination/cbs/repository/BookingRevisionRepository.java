package com.imagination.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagination.cbs.domain.BookingRevision;

/**
 * @author Ramesh.Suryaneni
 *
 */
public interface BookingRevisionRepository extends JpaRepository<BookingRevision, Long> {

}
