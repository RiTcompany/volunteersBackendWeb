package org.example.services;

import org.springframework.stereotype.Service;

@Service
public interface QrCodeService {
    byte[] generate(Long id, Long eventId);
}
