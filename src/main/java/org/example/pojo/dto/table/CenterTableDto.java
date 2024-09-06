package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;
import org.example.pojo.dto.LinkDto;

@Getter
@Setter
public class CenterTableDto {
    private Long id;
    private String name;
    private int participantCount;
    private String location;
    private String contact;
    private LinkDto teamLeader;
}
