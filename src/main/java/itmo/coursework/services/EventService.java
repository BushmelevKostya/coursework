package itmo.coursework.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.coursework.model.entity.Location;
import itmo.coursework.model.entity.OtherEvent;
import itmo.coursework.model.repository.LocationRepository;
import itmo.coursework.model.repository.OtherEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
	
	private static final String API_URL = "https://kudago.com/public-api/v1.4/events/";
	private final OtherEventRepository otherEventRepository;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final LocationRepository locationRepository;
	
	public EventService(OtherEventRepository otherEventRepository, LocationRepository locationRepository) {
		this.otherEventRepository = otherEventRepository;
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
		this.locationRepository = locationRepository;
	}
	
	public void fetchAndSaveCurrentEvents() {
		try {
			List<OtherEvent> events = new ArrayList<>();
			LocalDateTime filterDate = LocalDateTime.of(2024, 1, 1, 0, 0);
			Timestamp filterTimestamp = Timestamp.from(filterDate.toInstant(ZoneOffset.UTC));
			String url = API_URL + "?actual_since=" + filterTimestamp
					+ "&fields=title,description,location,dates&page_size=100";
			
			String response = restTemplate.getForObject(url, String.class);
			JsonNode root = objectMapper.readTree(response);
			
			for (JsonNode result : root.get("results")) {
				OtherEvent event = parseEvent(result);
				if (event != null && event.getDate().isAfter(filterDate)) {
					events.add(event);
				}
			}
			
			otherEventRepository.saveAll(events);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private OtherEvent parseEvent(JsonNode node) {
		try {
			String title = node.get("title").asText();
			String description = node.get("description").asText();
			JsonNode datesNode = node.get("dates");
			if (datesNode.isArray() && datesNode.size() > 0) {
				long dateTimestamp = datesNode.get(0).get("start").asLong();
				LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateTimestamp), ZoneId.systemDefault());
				
				OtherEvent event = new OtherEvent();
				event.setName(title);
				event.setDescription(description);
				event.setDate(date);
				Location location = new Location();
				location.setAddress(node.get("location").get("slug").asText());
				locationRepository.save(location);
				event.setLocation(location);
				return event;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
