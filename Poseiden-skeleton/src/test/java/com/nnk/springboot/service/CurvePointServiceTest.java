package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceTest {
    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;

    @Test
    public void getValidListOfCurvePoints() {
        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(new CurvePoint());
        curvePoints.add(new CurvePoint());

        when(curvePointRepository.findAll()).thenReturn(curvePoints);

        List<CurvePoint> toCheck = curvePointService.getCurvePoints();

        assertEquals(2, toCheck.size());
    }

    @Test
    public void getNullListOfCurvePoints() {
        when(curvePointRepository.findAll()).thenReturn(new ArrayList<>());

        List<CurvePoint> toCheck = curvePointService.getCurvePoints();

        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getValidCurvePoint() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(new CurvePoint()));

        CurvePoint toCheck = curvePointService.getCurvePointById(1);

        assertNotNull(toCheck);
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    public void cantGetCurvePointNotFound() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> curvePointService.getCurvePointById(1));

        assertTrue(runtimeException.getMessage().contains("Invalid CurvePoint"));
    }

    @Test
    public void addValidCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 100.00, 200.00);
        curvePoint.setId(1);

        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint toCheck = curvePointService.addCurvePoint(curvePoint);

        assertDoesNotThrow(() -> {});
        assertEquals(10, toCheck.getCurveId());
        assertEquals(100.00, toCheck.getTerm());
        assertEquals(200.00, toCheck.getValue());
    }

    @Test
    public void cantAddCurvePointNull() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> curvePointService.addCurvePoint(null));

        assertTrue(runtimeException.getMessage().contains("CurvePoint is null"));
    }

    @Test
    public void updateValidCurvePoint() {
        CurvePoint toUpdate = new CurvePoint(10, 100.00, 200.00);
        toUpdate.setId(1);

        CurvePoint curvePoint = new CurvePoint(1, 1.00, 1.00);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(curvePoint);

        CurvePoint toCheck = curvePointService.updateCurvePoint(1, curvePoint);

        assertEquals(1, toCheck.getCurveId());
        assertEquals(1.00, toCheck.getTerm());
        assertEquals(1.00, toCheck.getValue());
    }

    @Test
    public void cantUpdateCurvePointNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> curvePointService.updateCurvePoint(1, null));

        assertTrue(runtimeException.getMessage().contains("CurvePoint is null"));
    }

    @Test
    public void cantUpdateCurvePointNotFound() {
        CurvePoint bidList = new CurvePoint(10, 100.00, 200.00);

        when(curvePointRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> curvePointService.updateCurvePoint(1, bidList));

        assertTrue(runtimeException.getMessage().contains("CurvePoint with id not found"));
    }

    @Test
    public void deleteValidCurvePoint() {
        when(curvePointRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> curvePointService.deleteCurvePoint(1));
    }

    @Test
    public void cantDeleteCurvePointNotFound() {
        when(curvePointRepository.existsById(anyInt())).thenReturn(false);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> curvePointService.deleteCurvePoint(1));

        assertTrue(runtimeException.getMessage().contains("Invalid CurvePoint"));
    }
}