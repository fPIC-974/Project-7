package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService implements ITradeService {
    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public List<Trade> getTrades() {
        return tradeRepository.findAll();
    }

    @Override
    public Trade getTradeById(Integer id) {
        return tradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Trade not found"));
    }

    @Override
    public Trade addTrade(Trade trade) {
        if (trade == null) {
            throw new IllegalArgumentException("Invalid argument");
        }

        return tradeRepository.save(trade);
    }

    @Override
    public Trade updateTrade(Integer id, Trade trade) {
        if (trade == null) {
            throw new IllegalArgumentException("Invalid argument");
        }

        Trade toUpdate = tradeRepository.findById(id).orElseThrow(() -> new RuntimeException("Trade not found"));

        toUpdate.setAccount(trade.getAccount());
        toUpdate.setType(trade.getType());
        toUpdate.setBuyQuantity(trade.getBuyQuantity());

        return tradeRepository.save(toUpdate);
    }

    @Override
    public void deleteTrade(Integer id) {
        if (!tradeRepository.existsById(id)) {
            throw new RuntimeException("Trade not found");
        }

        tradeRepository.deleteById(id);
    }
}
