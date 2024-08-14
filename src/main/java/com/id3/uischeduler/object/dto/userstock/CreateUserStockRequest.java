package com.id3.uischeduler.object.dto.userstock;

import com.id3.uischeduler.object.dto.IDto;
import lombok.Builder;
import lombok.Data;

@Data
public class CreateUserStockRequest implements IDto {
    private String stock_symbol;
}
