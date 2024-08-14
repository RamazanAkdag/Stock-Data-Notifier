package com.id3.uischeduler.object.dto.stock;

import com.id3.uischeduler.object.dto.IDto;
import com.id3.uischeduler.object.entity.Stock;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class GetAllStocksResponse implements IDto {
    private List<Stock> stocks;
}
