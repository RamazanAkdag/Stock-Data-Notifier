package com.id3.uischeduler.object.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stock_data")
@Data
public class StockData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String last_price;
    private String stock_symbol;
    private String monthly_change;
    private String yearly_change;
    private String daily_change;
    private String volume;
    private String high_price;
    private String company_name;
    private String low_price;
    private String weekly_change;
    private String date;



}

