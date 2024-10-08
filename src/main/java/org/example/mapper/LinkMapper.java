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
        if (center == null) return null;

        LinkDto linkDto = new LinkDto();
        linkDto.setId(center.getId());
        linkDto.setName(center.getName());
        return linkDto;
    }

    public LinkDto headquarters(Headquarters headquarters) {
        if (headquarters == null) return null;

        LinkDto linkDto = new LinkDto();
        linkDto.setId(headquarters.getId());
        linkDto.setName(headquarters.getName());
        return linkDto;
    }

    public LinkDto event(Event event) {
        if (event == null) return null;

        LinkDto linkDto = new LinkDto();
        linkDto.setId(event.getId());
        linkDto.setName(event.getName());
        return linkDto;
    }

    public LinkDto participant(Volunteer volunteer) {
        if (volunteer == null) return null;

        LinkDto linkDto = new LinkDto();
        linkDto.setId(volunteer.getId());
        linkDto.setName(volunteer.getFullName());
        return linkDto;
    }

    public LinkDto document(Document document) {
        if (document == null) return null;

        LinkDto linkDto = new LinkDto();
        linkDto.setId(document.getId());
        linkDto.setName(document.getName());
        return linkDto;
    }
}
