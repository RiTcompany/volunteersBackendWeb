package org.example.services.impl;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.example.entities.Event;
import org.example.exceptions.EventNotFoundException;
import org.example.mapper.EventMapper;
import org.example.pojo.dto.card.EventCardDto;
import org.example.pojo.dto.create.EventCreateDto;
import org.example.pojo.dto.table.EventTableDto;
import org.example.pojo.dto.update.EventUpdateDto;
import org.example.repositories.EventRepository;
import org.example.services.EventService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    @Override
    public List<EventTableDto> getEventList() {
        return eventRepository.findAll().stream().map(eventMapper::eventDto).toList();
    }

    @Override
    public List<EventTableDto> getCenterEventList(Long centerId) {
        return eventRepository.findAllByCenterId(centerId).stream().map(eventMapper::eventDto).toList();
    }

    @Override
    public List<EventTableDto> getHeadquartersEventList(Long headquartersId) {
        return eventRepository.findAllByHeadquartersId(headquartersId).stream().map(eventMapper::eventDto).toList();
    }

    @Override
    public Long addEvent(EventCreateDto eventCreateDto) {
        return eventRepository.saveAndFlush(eventMapper.event(eventCreateDto)).getId();
    }

    @Override
    public void updateEvent(List<EventUpdateDto> updateDtoList) {
        updateDtoList.forEach(updateDto -> {
            Long id = updateDto.getId();
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new EventNotFoundException(id.toString()));
            event = eventMapper.event(event, updateDto);
            eventRepository.saveAndFlush(event);
        });
    }

    @Override
    public Long deleteEvent(Long id) {
        eventRepository.deleteById(id);
        return id;
    }

    @Override
    public EventCardDto getEventCardDto(Long id) {
        return eventRepository.findById(id).map(eventMapper::eventCardDto)
                .orElseThrow(() -> new EventNotFoundException(id.toString()));
    }

    @Override
    public Event addTrainingLink(Long eventId, String trainingLink) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId.toString()));
        event.setTrainingLink(trainingLink);
        return eventRepository.save(event);
    }

    @Override
    public Event addResultsLink(Long eventId, String resultsLink) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(eventId.toString()));
        event.setResultsLink(resultsLink);
        return eventRepository.save(event);
    }

    @Override
    public Boolean getResultByEmail(Long eventId, String email) throws GeneralSecurityException, IOException {
        String spreadsheetUrl = eventRepository.findEventResultsLinkById(eventId);

        String spreadsheetId = extractSpreadsheetId(spreadsheetUrl);
        if (spreadsheetId == null) {
            throw new IllegalArgumentException("Invalid Google Sheets URL.");
        }

        String range = "Sheet1!B1:C";

        String url = String.format(
                "https://sheets.googleapis.com/v4/spreadsheets/%s/values/%s?key=AIzaSyDfzw8ubHYAvVKDL5MK6VObX9Kq3eZdO80",
                spreadsheetId, range
        );

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("Failed to retrieve data. Response code: " + responseCode);
            return null;
        }

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        ValueRange response = new Gson().fromJson(reader, ValueRange.class);
        List<List<Object>> values = response.getValues();

        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
            return null;
        }

        for (List<Object> row : values) {
            if (row.size() > 0 && row.get(0).toString().equalsIgnoreCase(email)) {
                if (row.size() > 1 && areNumbersEqual(row.get(1).toString())) {
                    return true;
                }
            }
        }


        System.out.println("Email not found in the sheet.");
        return false;
    }

    private String extractSpreadsheetId(String url) {
        Pattern pattern = Pattern.compile("/spreadsheets/d/([a-zA-Z0-9-_]+)");
        Matcher matcher = pattern.matcher(url);
        return matcher.find() ? matcher.group(1) : null;
    }

    public boolean areNumbersEqual(String input) {
        if (input == null || !input.contains("/")) {
            return false;
        }
        input = input.replaceAll("\\s+", "");
        String[] parts = input.split("/");
        if (parts.length != 2) {
            return false;
        }
        try {
            int number1 = Integer.parseInt(parts[0]);
            int number2 = Integer.parseInt(parts[1]);
            return number1 == number2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
