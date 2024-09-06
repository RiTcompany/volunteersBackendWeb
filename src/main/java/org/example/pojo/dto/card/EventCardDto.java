package org.example.pojo.dto.card;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.LinkDto;

import java.util.Date;

@Getter
@Setter
public class EventCardDto {
    private Long id;
    private Date createDate;
    private Date startDate;
    private Date endDate;
    private String settingParticipantLink;
    private String groupChatLink;
    private String location;
    private LinkDto answerableLink;
    private String teamLeadLink; // TODO : переделать под ссылку
    private String registrationLink;
}
