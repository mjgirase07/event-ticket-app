package com.mahendra.tickets.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QrCodeConfig {
    public QRCodeWriter qrCodeWriter(){
        return new QRCodeWriter();
    }
}
