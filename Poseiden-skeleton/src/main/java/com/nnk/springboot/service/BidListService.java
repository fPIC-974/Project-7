package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BidListService implements IBidListService {
    private static final Logger logger = LogManager.getLogger(IBidListService.class);

    private final BidListRepository bidListRepository;

    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }

    @Override
    public List<BidList> getBidLists() {
        logger.info("Method called : getBidLists()");

        List<BidList> bidLists = bidListRepository.findAll();

        if (bidLists.isEmpty()) {
            logger.warn("No BidList found");
        }

        return bidLists;
    }

    @Override
    public BidList getBidList(int id) {
        logger.info("Method called : getBidList(" + id + ")");

        BidList bidList = bidListRepository.findById(id).orElse(null);

        if (bidList == null) {
            logger.error("BidList not found");
            throw new RuntimeException("BidList not found");
        }

        return bidList;
    }

    @Override
    public BidList addBidList(BidList bidList) {
        logger.info("Method called : addBidList(" + bidList + ")");

        if (bidList == null) {
            logger.error("BidList is null");
            throw new RuntimeException("BidList is null");
        }

        return bidListRepository.save(bidList);
    }

    @Override
    public BidList updateBidList(int id, BidList bidList) {
        logger.info("Method called : updateBidList(" + id + ", " + bidList + ")");
        if (bidList == null) {
            logger.error("BidList is null");
            throw new RuntimeException("BidList is null");
        }

        Optional<BidList> toUpdate = bidListRepository.findById(id);

        if (toUpdate.isEmpty()) {
            logger.error("BidList with id not found");
            throw new RuntimeException("BidList with id not found");
        }

        toUpdate.get().setAccount(bidList.getAccount());
        toUpdate.get().setType(bidList.getType());
        toUpdate.get().setBidQuantity(bidList.getBidQuantity());

        return bidListRepository.save(toUpdate.get());
    }

    @Override
    public void deleteBidListById(int id) {
        logger.info("Method called : deleteBidListById(" + id + ")");

        if (!bidListRepository.existsById(id)) {
            logger.error("BidList not found");
            throw new RuntimeException("BidList not found");
        }

        bidListRepository.deleteById(id);
    }
}
