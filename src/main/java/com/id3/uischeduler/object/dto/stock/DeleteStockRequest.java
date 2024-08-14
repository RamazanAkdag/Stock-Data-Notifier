package com.id3.uischeduler.object.dto.stock;

import com.id3.uischeduler.object.dto.IDto;
import lombok.Data;

@Data
public class DeleteStockRequest implements IDto {
    private Long id;
}
