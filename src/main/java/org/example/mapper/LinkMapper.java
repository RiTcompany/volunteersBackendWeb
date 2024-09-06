package org.example.mapper;

import org.example.entities.Center;
import org.example.entities.Document;
import org.example.entities.Event;
import org.example.entities.Headquarters;
import org.example.entities.Volunteer;
import org.example.pojo.dto.LinkDto;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper {
    public LinkDto center(Center center) {
        LinkDto linkDto = new LinkDto();
        linkDto.setId(center.getId());
        linkDto.setName(center.getName());
        return linkDto;
    }

    public LinkDto headquarters(Headquarters headquarters) {
        LinkDto linkDto = new LinkDto();
        linkDto.setId(headquarters.getId());
        linkDto.setName(headquarters.getName());
        return linkDto;
    }

    public LinkDto event(Event event) {
        LinkDto linkDto = new LinkDto();
        linkDto.setId(event.getId());
        linkDto.setName(event.getName());
        return linkDto;
    }

    public LinkDto participant(Volunteer volunteer) {
        LinkDto linkDto = new LinkDto();
        linkDto.setId(volunteer.getId());
        linkDto.setName(volunteer.getFullName());
        return linkDto;
    }

    public LinkDto document(Document document) {
        LinkDto linkDto = new LinkDto();
        linkDto.setId(document.getId());
        linkDto.setName(document.getName());
        return linkDto;
    }
}
