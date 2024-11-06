package org.example.pojo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PresenceMarkRequest {
    Long adminId;
    Long equipmentId;
    Long eventId;
}
