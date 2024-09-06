package org.example.pojo.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DocumentCreateDto {
    private String name;
    private String sender;
    private String recipient;
    private Date createDate;
    private boolean approvalControl;
}
