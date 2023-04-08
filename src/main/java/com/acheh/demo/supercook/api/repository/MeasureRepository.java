package com.acheh.demo.supercook.api.repository;

import com.acheh.demo.supercook.api.model.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends JpaRepository<Measure, Integer> {
}
