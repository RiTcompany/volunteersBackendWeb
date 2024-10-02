package org.example.pojo.dto.update;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EventUpdateDto {
    private Long id;
    private String name;
    private Date startTime;
    private Date endTime;
    private String location;
    private String teamLeader;
}
