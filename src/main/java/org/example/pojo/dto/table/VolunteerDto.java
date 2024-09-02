package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.BirthdayDto;

import java.util.List;

@Getter
@Setter
public class VolunteerDto {
    private String id;
    private String fullName;
    private BirthdayDto birthdayDto;
    private String tgLink;
    private String vk;
    private String color;
    private List<LinkDto> eventLinkList;
    private String comment;
    private double rank;
    private boolean hasInterview;
    private String level;
    private LinkDto centerLink;
    private LinkDto HeadquartersLink;
}
