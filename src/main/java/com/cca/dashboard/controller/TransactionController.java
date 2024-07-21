package com.cca.dashboard.controller;

import org.springframework.web.bind.annotation.RestController;

import com.cca.dashboard.entity.PaytmDetails;
import com.cca.dashboard.entity.Transaction;
import com.cca.dashboard.service.TransactionService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = {"http://localhost:3000"})
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/initiateTransaction")
    public ResponseEntity<Transaction> transactionInitiate(@RequestParam("customer_id") String cust_id,
            @RequestParam("order_id") String order_id, @RequestParam("ammount") String ammount) {
        Transaction transaction = new Transaction();
        Order order = null;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("amount", (Integer.parseInt(ammount) * 100));
        jsonObject.put("currency", "INR");
        try {
            RazorpayClient razorpayClient = new RazorpayClient("rzp_test_KHkKUM5Faj08Ha", "KhyrdO3YSrk9SlhgbOXCgzwJ");
            order = razorpayClient.orders.create(jsonObject);
            System.out.println(order);
        } catch (RazorpayException e) {
            e.printStackTrace();
        }
        
        transaction.setAmount("2.00");
        transaction.setId((String) order.get("id"));
        transaction.setDescription((String) order.get("status"));
        transaction.setStudentid(cust_id);
        transaction.setDate("20-07-2024");
        return ResponseEntity.ok(transaction);
    }

   @PostMapping("/verifyPayment")
    public ResponseEntity<Map<String, String>> verifyPayment(@RequestParam("razorpay_payment_id") String razorpay_payment_id,
    @RequestParam("razorpay_order_id") String razorpay_order_id, @RequestParam("razorpay_signature") String razorpay_signature,
    @RequestParam("amount") String amount, @RequestParam("studentid") String studentid) {
       

        String generatedSignature = generateSignature(razorpay_order_id + "|" + razorpay_payment_id, "KhyrdO3YSrk9SlhgbOXCgzwJ");

        Map<String, String> response = new HashMap<>();
        System.out.println(generatedSignature.equals(razorpay_signature));
        if (generatedSignature.equals(razorpay_signature)) {
            Transaction transaction = new Transaction(razorpay_payment_id,amount,"Payment recieved",LocalDate.now().toString(),studentid,"success");
            transactionService.saveTransaction(transaction);
            response.put("status", "success");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("status", "failure");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    private String generateSignature(String data, String key) {
        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] signedBytes = sha256Hmac.doFinal(data.getBytes());
            StringBuilder signature = new StringBuilder();
            for (byte b : signedBytes) {
                signature.append(String.format("%02x", b));
            }
            return signature.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate HMAC SHA256 signature", e);
        }
    }
}
