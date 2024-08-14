package com.id3.uischeduler.repository;

import com.id3.uischeduler.object.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockRepository extends JpaRepository<Stock, Long> {
}
