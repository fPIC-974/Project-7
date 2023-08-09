package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;

import java.util.List;

public interface IBidListService {
    List<BidList> getBidLists();

    BidList getBidList(int id);

    BidList addBidList(BidList bidList);

    BidList updateBidList(int id, BidList bidList);

    void deleteBidListById(int id);
}
