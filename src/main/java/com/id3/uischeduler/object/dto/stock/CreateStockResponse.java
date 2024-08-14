package com.id3.uischeduler.object.dto.stock;

import com.id3.uischeduler.object.dto.IDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CreateStockResponse implements IDto {
    private Long id;
    private String stockSymbol;
    private String message;
    private LocalDateTime timestamp;
}
