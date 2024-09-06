package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventTableDto {
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private String location;
    private String teamLeader;
    private String registrationLink;
}
