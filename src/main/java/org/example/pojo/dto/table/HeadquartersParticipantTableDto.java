package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.BirthdayDto;

@Getter
@Setter
public class HeadquartersParticipantTableDto {
    private Long id;
    private Long volunteerId;
    private String fullName;
    private BirthdayDto birthday;
    private String tgLink;
    private String vkLink;
    private String color;
    private String comment;
    private double rank;
    private boolean interview;
    private String level;
}
