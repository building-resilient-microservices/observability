package com.example.messaging.repository;

import com.example.messaging.model.Fact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactRepository extends CrudRepository<Fact, Integer> { }
