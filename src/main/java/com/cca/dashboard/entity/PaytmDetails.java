package com.cca.dashboard.entity;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("paytm.payment.sandbox")
@ToString
@Component
public class PaytmDetails {
    private String merchantId;
    private String merchantKey;
    private String channelId;
    private String website;
    private String industryTypeId;
    private String paytmUrl;
    private Map<String,String> details; 
}
