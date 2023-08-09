package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListServiceTest {
    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;

    @Test
    public void getValidListOfBidLists() {
        List<BidList> bidList = new ArrayList<>();
        bidList.add(new BidList());
        bidList.add(new BidList());

        when(bidListRepository.findAll()).thenReturn(bidList);

        List<BidList> toCheck = bidListService.getBidLists();

        assertEquals(2, toCheck.size());
    }

    @Test
    public void getNullListOfBidLists() {
        when(bidListRepository.findAll()).thenReturn(new ArrayList<>());

        List<BidList> toCheck = bidListService.getBidLists();

        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getValidBidList() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(new BidList()));

        BidList toCheck = bidListService.getBidList(1);

        assertNotNull(toCheck);
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    public void cantGetBidListNotFound() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> bidListService.getBidList(1));

        assertTrue(runtimeException.getMessage().contains("BidList not found"));
    }

    @Test
    public void addValidBidList() {
        BidList bidList = new BidList("Test", "TEST", 100.00);
        bidList.setBidListId(1);

        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList toCheck = bidListService.addBidList(bidList);

        assertDoesNotThrow(() -> {});
        assertEquals("Test", toCheck.getAccount());
        assertEquals("TEST", toCheck.getType());
        assertEquals(100.00, toCheck.getBidQuantity());
    }

    @Test
    public void cantAddBidListNull() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> bidListService.addBidList(null));

        assertTrue(runtimeException.getMessage().contains("BidList is null"));
    }

    @Test
    public void updateValidBidList() {
        BidList toUpdate = new BidList("Test", "TEST", 100.00);
        toUpdate.setBidListId(1);

        BidList bidList = new BidList("Updated", "UPDATED", 0.00);

        when(bidListRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(bidListRepository.save(any(BidList.class))).thenReturn(bidList);

        BidList toCheck = bidListService.updateBidList(1, bidList);

        assertEquals("Updated", toCheck.getAccount());
        assertEquals("UPDATED", toCheck.getType());
        assertEquals(0.00, toCheck.getBidQuantity());
    }

    @Test
    public void cantUpdateBidListNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> bidListService.updateBidList(1, null));

        assertTrue(runtimeException.getMessage().contains("BidList is null"));
    }

    @Test
    public void cantUpdateBidListNotFound() {
        BidList bidList = new BidList("Updated", "UPDATED", 0.00);

        when(bidListRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> bidListService.updateBidList(1, bidList));

        assertTrue(runtimeException.getMessage().contains("BidList with id not found"));
    }

    @Test
    public void deleteValidBidListById() {
        when(bidListRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> bidListService.deleteBidListById(1));
    }

    @Test
    public void cantDeleteBidListByIdNotFound() {
        when(bidListRepository.existsById(anyInt())).thenReturn(false);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> bidListService.deleteBidListById(1));

        assertTrue(runtimeException.getMessage().contains("BidList not found"));
    }
}