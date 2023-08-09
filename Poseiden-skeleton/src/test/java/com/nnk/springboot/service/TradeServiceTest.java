package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    public void getValidListOfTrades() {
        List<Trade> tradeList = new ArrayList<>();
        tradeList.add(new Trade());
        tradeList.add(new Trade());

        when(tradeRepository.findAll()).thenReturn(tradeList);

        List<Trade> toCheck = tradeService.getTrades();

        assertEquals(2, toCheck.size());
    }

    @Test
    public void getNullListOfTrades() {
        when(tradeRepository.findAll()).thenReturn(new ArrayList<>());

        List<Trade> toCheck = tradeService.getTrades();

        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getValidTrade() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(new Trade()));

        Trade toCheck = tradeService.getTradeById(1);

        assertNotNull(toCheck);
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    public void cantGetTradeNotFound() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> tradeService.getTradeById(1));

        assertTrue(runtimeException.getMessage().contains("Trade not found"));
    }

    @Test
    public void addValidTrade() {
        Trade trade = new Trade("Test", "TEST");
        trade.setTradeId(1);

        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade toCheck = tradeService.addTrade(trade);

        assertDoesNotThrow(() -> {});
        assertEquals("Test", toCheck.getAccount());
        assertEquals("TEST", toCheck.getType());
    }

    @Test
    public void cantAddTradeNull() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> tradeService.addTrade(null));

        assertTrue(runtimeException.getMessage().contains("Invalid argument"));
    }

    @Test
    public void updateValidTrade() {
        Trade toUpdate = new Trade("Test", "TEST");
        toUpdate.setTradeId(1);

        Trade trade = new Trade("Updated", "UPDATED");

        when(tradeRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade toCheck = tradeService.updateTrade(1, trade);

        assertEquals("Updated", toCheck.getAccount());
        assertEquals("UPDATED", toCheck.getType());
    }

    @Test
    public void cantUpdateTradeNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> tradeService.updateTrade(1, null));

        assertTrue(runtimeException.getMessage().contains("Invalid argument"));
    }

    @Test
    public void cantUpdateTradeNotFound() {
        Trade trade = new Trade("Updated", "UPDATED");

        when(tradeRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> tradeService.updateTrade(1, trade));

        assertTrue(runtimeException.getMessage().contains("Trade not found"));
    }

    @Test
    public void deleteValidTradeById() {
        when(tradeRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> tradeService.deleteTrade(1));
    }

    @Test
    public void cantDeleteTradeByIdNotFound() {
        when(tradeRepository.existsById(anyInt())).thenReturn(false);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> tradeService.deleteTrade(1));

        assertTrue(runtimeException.getMessage().contains("Trade not found"));
    }
}