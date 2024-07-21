package com.cca.dashboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TrasactionRequest {
    private String redirctUrl;
    private long mobileno;
    private String email;
    private String checksumhash;
    private String cust_id;
    private String order_id;
    private String ammount;

}
