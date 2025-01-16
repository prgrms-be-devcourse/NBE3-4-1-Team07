package com.ll.backend.domain.order.controller;

import com.ll.backend.domain.admin.service.AdminNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class PaymentController {
    private final AdminNotificationService adminNotificationService;

    //결제 완료 시 호출
    @PostMapping("/order/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable Long orderId) {
        adminNotificationService.notifyAdminForShipping(orderId);
        return ResponseEntity.ok("Payment processed and admin notified for shipping.");
    }
}
