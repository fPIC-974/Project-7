package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.IBidListService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;


@Controller
public class BidListController {
    // TODO: Inject Bid service
    private final IBidListService IBidListService;

    public BidListController(IBidListService IBidListService) {
        this.IBidListService = IBidListService;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, Principal principal)
    {
        model.addAttribute("bidList", IBidListService.getBidLists());
        model.addAttribute("user", principal.getName());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        if (result.hasErrors()) {
            if (Objects.equals(Objects.requireNonNull(result.getFieldError("bidQuantity")).getCode(), "typeMismatch")) {
                model.addAttribute("numFieldError", "Invalid Number Format");
            } else {
                model.addAttribute("numFiledError", result.getFieldError());
            }
            return "bidList/add";
        }

        IBidListService.addBidList(bid);
        model.addAttribute("bidList", IBidListService.getBidLists());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = IBidListService.getBidList(id);
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (result.hasErrors()) {
            if (Objects.equals(Objects.requireNonNull(result.getFieldError("bidQuantity")).getCode(), "typeMismatch")) {
                model.addAttribute("numFieldError", "Invalid Number Format");
            }
            return "bidList/update";
        }

        IBidListService.updateBidList(id, bidList);
        model.addAttribute("bidList", IBidListService.getBidLists());

        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        IBidListService.deleteBidListById(id);
        model.addAttribute("bidList", IBidListService.getBidLists());
        return "redirect:/bidList/list";
    }
}
