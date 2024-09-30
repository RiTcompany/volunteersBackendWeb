package org.example.pojo.dto.card;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.BirthdayDto;
import org.example.pojo.dto.LinkDto;

import java.util.List;

@Getter
@Setter
public class PersonalAccountDto {
    private Long volunteerId;
    private String fullName;
    private BirthdayDto birthdayDto;
    private String tgLink;
    private String vkLink;
    private Double rank;
    private List<LinkDto> eventLinkList;
    private LinkDto centerLink;
    private LinkDto headquartersLink;
    private Long districtTeamId;
}
