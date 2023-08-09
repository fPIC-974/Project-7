package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurvePointService implements ICurvePointService {
    private static final Logger logger = LogManager.getLogger(ICurvePointService.class);
    private final CurvePointRepository curvePointRepository;

    public CurvePointService(CurvePointRepository curvePointRepository) {
        this.curvePointRepository = curvePointRepository;
    }

    @Override
    public List<CurvePoint> getCurvePoints() {
        logger.info("Method called : getCurvePoints()");

        List<CurvePoint> curvePoints = curvePointRepository.findAll();

        if (curvePoints.isEmpty()) {
            logger.warn("No CurvePoints found");
        }

        return curvePoints;
    }

    @Override
    public CurvePoint getCurvePointById(Integer id) {
        return curvePointRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid CurvePoint"));
    }

    @Override
    public CurvePoint addCurvePoint(CurvePoint curvePoint) {
        if (curvePoint == null) {
            throw new RuntimeException("CurvePoint is null");
        }

        return curvePointRepository.save(curvePoint);
    }

    @Override
    public CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint) {
        if (curvePoint == null) {
            throw new RuntimeException("CurvePoint is null");
        }

        CurvePoint toUpdate = curvePointRepository.findById(id).orElseThrow(() -> new RuntimeException("CurvePoint with id not found"));

        toUpdate.setCurveId(curvePoint.getCurveId());
        toUpdate.setTerm(curvePoint.getTerm());
        toUpdate.setValue(curvePoint.getValue());

        return curvePointRepository.save(toUpdate);
    }

    @Override
    public void deleteCurvePoint(Integer id) {
        if (!curvePointRepository.existsById(id)) {
            throw new RuntimeException("Invalid CurvePoint");
        }

        curvePointRepository.deleteById(id);
    }
}
