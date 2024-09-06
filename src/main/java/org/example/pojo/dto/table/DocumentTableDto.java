package org.example.pojo.dto.table;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DocumentTableDto {
    private Long id;
    private String name;
    private String sender;
    private String recipient;
    private Date createDate;
    private boolean approvalControl;
}
