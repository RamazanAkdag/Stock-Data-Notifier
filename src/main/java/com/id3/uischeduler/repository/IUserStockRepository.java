package com.id3.uischeduler.repository;

import com.id3.uischeduler.object.entity.UserStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserStockRepository extends JpaRepository<UserStock, Long> {
}
