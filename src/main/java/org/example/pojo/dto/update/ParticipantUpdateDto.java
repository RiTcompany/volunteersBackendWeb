package org.example.pojo.dto.update;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.EColor;

import java.util.Date;

@Getter
@Setter
public class ParticipantUpdateDto {
    private Long id;
    private String fullName;
    private Date birthday;
    private String tgLink;
    private String vkLink;
    private EColor color;
    private String comment;
    private Boolean hasInterview;
    private String level;
    private Long centerId;
    private Long headquartersId;
    private Boolean testing;
    private Double rank;
    private Boolean hasClothes;
}
