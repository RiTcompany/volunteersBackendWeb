package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.EParticipant;
import org.example.pojo.dto.BirthdayDto;

@Getter
@Setter
public class EventParticipantDto {
    private String id;
    private String fullName;
    private BirthdayDto birthdayDto;
    private String tgLink;
    private String functional;
    private boolean testing;
    private String comment;
    private double rank;
    private boolean hasClothes;
}
