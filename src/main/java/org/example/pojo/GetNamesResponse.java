package org.example.pojo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetNamesResponse {
    String volunteerName;
    String eventName;
}
