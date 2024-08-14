package com.id3.uischeduler.object.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "all_stocks")
@Data
public class Stock implements IEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stock_symbol;

}
