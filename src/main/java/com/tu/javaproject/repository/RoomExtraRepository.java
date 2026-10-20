package com.tu.javaproject.repository;

import com.tu.javaproject.model.RoomExtra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomExtraRepository extends JpaRepository<RoomExtra, Long> {

    Optional<RoomExtra> findByName(String name);
}
