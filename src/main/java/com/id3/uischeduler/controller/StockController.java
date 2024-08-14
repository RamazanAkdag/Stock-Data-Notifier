package com.id3.uischeduler.controller;

import com.id3.uischeduler.object.dto.userstock.CreateUserStockRequest;
import com.id3.uischeduler.object.dto.userstock.DeleteUserStockRequest;
import com.id3.uischeduler.object.entity.Stock;
import com.id3.uischeduler.object.entity.UserStock;
import com.id3.uischeduler.service.IStockService;
import com.id3.uischeduler.service.IUserStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final IUserStockService userStockService;
    private final IStockService stockService;

    @GetMapping
    public String showStocks(Model model) {

        var userStocksResponse = userStockService.getAll();

        List<String> userStocks = userStocksResponse.getUserStocks().stream()
                .map(UserStock::getStock_symbol)
                .collect(Collectors.toList());


        List<String> availableStocks = stockService.getAll().getStocks().stream()
                .map(Stock::getStock_symbol)
                .filter(stock -> !userStocks.contains(stock))
                .collect(Collectors.toList());

        model.addAttribute("availableStocks", availableStocks);
        model.addAttribute("userStocks", userStocks);

        return "stocks";
    }

    @PostMapping("/follow/{stock}")
    public String followStock(@PathVariable String stock) {
        CreateUserStockRequest request = new CreateUserStockRequest();
        request.setStock_symbol(stock);
        userStockService.insert(request);
        return "redirect:/stocks";
    }

    @PostMapping("/unfollow/{stock}")
    public String unfollowStock(@PathVariable String stock) {
        var userStocksResponse = userStockService.getAll();
        var stockToUnfollow = userStocksResponse.getUserStocks().stream()
                .filter(s -> s.getStock_symbol().equals(stock))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        DeleteUserStockRequest request = new DeleteUserStockRequest();
        request.setId(stockToUnfollow.getId());
        userStockService.delete(request);
        return "redirect:/stocks";
    }

}
