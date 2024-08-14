package com.id3.uischeduler.service;

import com.id3.uischeduler.object.dto.userstock.*;

public interface IUserStockService {

    public GetAllUserStocksResponse getAll();

    public CreateUserStockResponse insert(CreateUserStockRequest request);

    public DeleteUserStockResponse delete(DeleteUserStockRequest request);
}
