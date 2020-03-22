package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.LocationStats;
import com.example.demo.service.CoronaVirusDataService;

@RestController
public class LocationController {

	@Autowired
	CoronaVirusDataService coronaVirusDataService;

	@GetMapping("/findAllLocations")
	public List<LocationStats> findAllLocations() {
		List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        for (LocationStats locationStats : allStats) {
			locationStats.getState();
			locationStats.getCountry();
			locationStats.getLatestTotalCases();
			locationStats.getDiffFromPrevDay();
		}
		return allStats;
	}

//	List<LocationStats> allStats = coronaVirusDataService.getAllStats();
//  int totalReportedCases = allStats.stream().mapToInt(stat -> stat.getLatestTotalCases()).sum();
//  int totalNewCases = allStats.stream().mapToInt(stat -> stat.getDiffFromPrevDay()).sum();
//  model.addAttribute("locationStats", allStats);
//  model.addAttribute("totalReportedCases", totalReportedCases);
//  model.addAttribute("totalNewCases", totalNewCases);

}
