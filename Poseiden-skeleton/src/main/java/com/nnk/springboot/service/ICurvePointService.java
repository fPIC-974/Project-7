package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;

import java.util.List;

public interface ICurvePointService {
    List<CurvePoint> getCurvePoints();

    CurvePoint getCurvePointById(Integer id);

    CurvePoint addCurvePoint(CurvePoint curvePoint);

    CurvePoint updateCurvePoint(Integer id, CurvePoint curvePoint);

    void deleteCurvePoint(Integer id);
}
