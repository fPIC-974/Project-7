package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;

import java.util.List;

public interface ITradeService {
    List<Trade> getTrades();

    Trade getTradeById(Integer id);

    Trade addTrade(Trade trade);

    Trade updateTrade(Integer id, Trade trade);

    void deleteTrade(Integer id);
}
