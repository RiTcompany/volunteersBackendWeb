package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.services.QrCodeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping("/generateQR/volunteer/{volunteerId}/event/{eventId}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable Long volunteerId, @PathVariable Long eventId) {
        byte[] qrCode = qrCodeService.generate(volunteerId,eventId);
        return qrCode != null ? ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"qrcode.png\"")
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode) : ResponseEntity.badRequest().build();
    }
}
