package org.example.exceptions;

public class VolunteerEquipmentNotFoundException extends RuntimeException {
    public VolunteerEquipmentNotFoundException(Long volunteerId, Long equipmentId, Long adminId) {
        super("Администратор с идентификатором " + adminId
                + " не выдавал экипировку под идентификатором " + equipmentId
                + " волонтеру с идентификатором " + volunteerId);
    }
}
