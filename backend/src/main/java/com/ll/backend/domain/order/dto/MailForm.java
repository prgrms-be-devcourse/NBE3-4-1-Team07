package com.ll.backend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailForm {
    private String from;
    private String to;
    private String title;
    private String message;
}