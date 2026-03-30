package com.example.demo.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.IngredientEntity;

import jakarta.persistence.LockModeType;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM IngredientEntity i WHERE i.id IN :ids")
    List<IngredientEntity> findAllByIdForUpdate(@Param("ids") Set<Long> ids);
}
