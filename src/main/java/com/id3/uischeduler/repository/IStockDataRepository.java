package com.id3.uischeduler.repository;

import com.id3.uischeduler.object.entity.StockData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStockDataRepository extends JpaRepository<StockData, Long> {
}

