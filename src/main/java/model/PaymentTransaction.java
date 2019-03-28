package model;


import lombok.*;


@Builder
@Data
public class PaymentTransaction {
    private Double transaction_amount;
    private String user_id;
}
