package itmo.coursework.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import itmo.coursework.model.entity.OtherEvent;
import itmo.coursework.model.repository.OtherEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
	
	private static final String API_URL = "https://kudago.com/public-api/v1.4/events/";
	private final OtherEventRepository otherEventRepository;
	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	
	public EventService(OtherEventRepository otherEventRepository) {
		this.otherEventRepository = otherEventRepository;
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
	}
	
	public void fetchAndSaveCurrentEvents() {
		try {
			long currentTimestamp = Instant.now().getEpochSecond();
			String url = API_URL + "?actual_since=" + currentTimestamp
					+ "&fields=title,description,location,dates&page_size=100";
			
			String response = restTemplate.getForObject(url, String.class);
			JsonNode root = objectMapper.readTree(response);
			
			List<OtherEvent> events = new ArrayList<>();
			for (JsonNode result : root.get("results")) {
				OtherEvent event = parseEvent(result);
				if (event != null) {
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
				return event;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
