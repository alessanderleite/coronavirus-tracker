package com.example.demo.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.demo.model.LocationStats;

@Service
public class CoronaVirusDataService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CoronaVirusDataService.class);
	private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
	
	private List<LocationStats> allStats = new ArrayList<>();
	
	public List<LocationStats> getAllStats() {
		return allStats;
	}
	
	@PostConstruct
	@Scheduled(cron = "* * 1 * * *")
	public void fetchVirusData() throws IOException, InterruptedException {
		List<LocationStats> newStats = new ArrayList<>();
		try {
			
			URL url = new URL(VIRUS_DATA_URL);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

			try {
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				final Reader csvBodyReader = new InputStreamReader(in); 
				final Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

				for (CSVRecord record : records) {
						LocationStats locationStat = new LocationStats();
						locationStat.setState(record.get("Province/State"));
						locationStat.setCountry(record.get("Country/Region"));
						int latestCases = Integer.parseInt(record.get(record.size() - 1));
						int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
						locationStat.setLatestTotalCases(latestCases);
						locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
						newStats.add(locationStat);
				}	
				csvBodyReader.close();
			} finally {
				urlConnection.disconnect();
			}
		} catch (MalformedURLException e) {
			LOGGER.info("Error " + e);
		} catch (IOException e) {
			LOGGER.info("Error " + e);
		}

		this.allStats = newStats;
		System.out.println("output: " + allStats);
	}
}