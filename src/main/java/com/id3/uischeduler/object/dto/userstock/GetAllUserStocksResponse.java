package com.id3.uischeduler.object.dto.userstock;

import com.id3.uischeduler.object.entity.UserStock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllUserStocksResponse {
    private List<UserStock> userStocks;
}
