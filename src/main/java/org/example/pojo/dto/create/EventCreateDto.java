package org.example.pojo.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventCreateDto {
    private String name;
    private Date startTime;
    private Date endTime;
    private String location;
    private String teamLeader;
    private String setParticipantLink;
    private String groupChatLink;
    private Long federalId;
    private String settingParticipantLink;
    private Long answerableVolunteerId;
    private String registrationLink;
}
