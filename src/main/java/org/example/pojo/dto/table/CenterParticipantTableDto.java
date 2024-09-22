package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.enums.EColor;

import java.util.Date;

@Getter
@Setter
public class CenterParticipantTableDto {
    private Long id;
    private Long volunteerId;
    private String fullName;
    private Date birthday;
    private String tgLink;
    private String vkLink;
    private EColor color;
    private String comment;
    private double rank;
    private boolean interview;
    private String level;
}
