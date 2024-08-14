package com.id3.uischeduler.service;

import com.id3.uischeduler.object.dto.stock.*;
import com.id3.uischeduler.object.entity.Stock;
import com.id3.uischeduler.repository.IStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StockManager implements IStockService {
    private final IStockRepository stockRepository;

    public GetAllStocksResponse getAll() {
        var stocks = stockRepository.findAll();
        return GetAllStocksResponse.builder().stocks(stocks).build();
    }

    public CreateStockResponse insert(CreateStockRequest request) {
        Stock stock = new Stock();
        stock.setStock_symbol(request.getStockSymbol());

        var inserted = stockRepository.save(stock);
        return CreateStockResponse.builder()
                .stockSymbol(inserted.getStock_symbol())
                .timestamp(LocalDateTime.now())
                .id(inserted.getId())
                .message("Stock inserted successfully")
                .build();
    }

    public DeleteStockResponse delete(DeleteStockRequest request) {
        stockRepository.deleteById(request.getId());

        return DeleteStockResponse.builder()
                .id(request.getId())
                .message("Stock successfully deleted.")
                .timestamp(LocalDateTime.now())
                .build();
    }
}

