package com.id3.uischeduler.service;

import com.id3.uischeduler.object.dto.stock.*;


public interface IStockService {
    public GetAllStocksResponse getAll();

    public CreateStockResponse insert(CreateStockRequest request);

    public DeleteStockResponse delete(DeleteStockRequest request);
}
