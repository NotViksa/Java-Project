package com.tu.javaproject.repository;

import com.tu.javaproject.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    Optional<Season> findByName(String name);
}
