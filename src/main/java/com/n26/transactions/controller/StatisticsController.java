package com.n26.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.assembler.StatisticsAssembler;
import com.n26.transactions.model.StatisticsData;
import com.n26.transactions.resources.StatisticsResponseResource;
import com.n26.transactions.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    private StatisticsAssembler statisticsAssembler;

    @GetMapping
    public ResponseEntity<StatisticsResponseResource> getStatistics() {
        StatisticsData statisticsData = statisticsService.getStatistics();
        StatisticsResponseResource responseResource = statisticsAssembler.getResponseData(statisticsData);
        return new ResponseEntity<StatisticsResponseResource>(responseResource, HttpStatus.OK);
    }
}
