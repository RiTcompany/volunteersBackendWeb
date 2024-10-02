package org.example.pojo.dto.card;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.LinkDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class HeadquartersCardDto {
    private String name;
    private Long federalId;
    private Date createDate;
    private List<String> tgLinkList;
    private List<String> vkLinkList;
    private Double rank;
    private List<LinkDto> eventLinkList;
    private List<LinkDto> documentLinkList;
    private List<LinkDto> participantLinkList;
    private List<LinkDto> equipmentLinkList;
}
