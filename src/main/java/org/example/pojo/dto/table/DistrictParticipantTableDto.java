package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.BirthdayDto;
import org.example.pojo.dto.LinkDto;

import java.util.List;

@Getter
@Setter
public class DistrictParticipantTableDto {
    private Long volunteerId;
    private String fullName;
    private BirthdayDto birthdayDto;
    private String tgLink;
    private String vkLink;
    private String color;
    private List<LinkDto> eventLinkList;
}
