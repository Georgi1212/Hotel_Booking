package com.app.hotelbooking.controller;

import com.app.hotelbooking.dto.OrderDto;
import com.app.hotelbooking.service.PayPalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class PayPalController {

    private final PayPalService payPalService;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("pay")
    public String payment(@RequestParam("sum") String sumPrice,
                          @RequestParam("hotelId") Long hotelId, @RequestParam("roomId") Long roomId,
                          @RequestParam("check_in") LocalDate check_in, @RequestParam("check_out") LocalDate check_out) {
        try {
            Payment payment = payPalService.createPayment(sumPrice, hotelId, roomId, check_in, check_out);

            for(Links link : payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }

        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }

        return "";
    }

    /*@GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }*/

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = payPalService.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}