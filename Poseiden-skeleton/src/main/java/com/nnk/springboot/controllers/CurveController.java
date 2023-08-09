package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.ICurvePointService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Objects;


@Controller
public class CurveController {
    // TODO: Inject Curve Point service
    private final ICurvePointService ICurvePointService;

    public CurveController(ICurvePointService ICurvePointService) {
        this.ICurvePointService = ICurvePointService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model, Principal principal)
    {
        // TODO: find all Curve Point, add to model
        model.addAttribute("user", principal.getName());
        model.addAttribute("curvePoints", ICurvePointService.getCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (result.hasErrors()) {
            if (Objects.equals(Objects.requireNonNull(result.getFieldError("value")).getCode(), "typeMismatch")) {
                model.addAttribute("numFieldError", "Invalid Number Format");
            } else {
                model.addAttribute("numFieldError", result.getFieldError("value").getDefaultMessage());

            }
            return "curvePoint/add";
        }

        ICurvePointService.addCurvePoint(curvePoint);
        model.addAttribute("curvePoints", ICurvePointService.getCurvePoints());

        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = ICurvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (result.hasErrors()) {
            if (Objects.equals(Objects.requireNonNull(result.getFieldError("value")).getCode(), "typeMismatch")) {
                model.addAttribute("numFieldError", "Invalid Number Format");
            } else {
                model.addAttribute("numFieldError", result.getFieldError("value").getDefaultMessage());

            }
            return "curvePoint/update";
        }

        ICurvePointService.updateCurvePoint(id, curvePoint);
        model.addAttribute("curvePoints", ICurvePointService.getCurvePoints());

        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        ICurvePointService.deleteCurvePoint(id);
        model.addAttribute("curvePoints", ICurvePointService.getCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
