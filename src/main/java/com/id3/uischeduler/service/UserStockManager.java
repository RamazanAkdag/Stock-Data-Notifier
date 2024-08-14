package com.id3.uischeduler.service;

import com.id3.uischeduler.object.dto.userstock.*;
import com.id3.uischeduler.object.entity.UserStock;
import com.id3.uischeduler.repository.IUserStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserStockManager implements IUserStockService {
    private final IUserStockRepository userStockRepository;

    @Override
    public GetAllUserStocksResponse getAll() {
        var userStocks = userStockRepository.findAll();
        return GetAllUserStocksResponse.builder().userStocks(userStocks).build();
    }

    @Override
    public CreateUserStockResponse insert(CreateUserStockRequest request) {
        UserStock userStock = new UserStock();
        userStock.setStock_symbol(request.getStock_symbol());

        var inserted = userStockRepository.save(userStock);
        return CreateUserStockResponse.builder()
                .stockSymbol(inserted.getStock_symbol())
                .timestamp(LocalDateTime.now())
                .id(inserted.getId())
                .message("inserted successfully")
                .build();
    }

    @Override
    public DeleteUserStockResponse delete(DeleteUserStockRequest request) {
        userStockRepository.deleteById(request.getId());

        return DeleteUserStockResponse.builder()
                .id(request.getId())
                .message("Stock successfully deleted.")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
