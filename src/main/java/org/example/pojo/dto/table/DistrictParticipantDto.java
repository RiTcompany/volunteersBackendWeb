package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.BirthdayDto;

import java.util.List;

@Getter
@Setter
public class DistrictParticipantDto {
    private String id;
    private String fullName;
    private BirthdayDto birthdayDto;
    private String tgLink;
    private String vkLink;
    private String color;
    private List<LinkDto> eventLinkList;
}
