package model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionVolume {

    private String user_id;
    private double amount;
    private long count;

    public static TransactionVolume sum(TransactionVolume tv1, TransactionVolume tv2) {
        TransactionVolumeBuilder builder = newBuilder(tv1);
        builder
                .amount(tv1.getAmount() + tv2.getAmount())
                .count(tv1.getCount() + tv2.getCount());
        return builder.build();
    }

    public static TransactionVolumeBuilder newBuilder(PaymentTransaction paymentTransaction) {
        TransactionVolumeBuilder builder = new TransactionVolumeBuilder();
        builder
                .user_id(paymentTransaction.getUser_id())
                .amount(paymentTransaction.getTransaction_amount())
                .count(1);
        return builder;
    }

    public static TransactionVolumeBuilder newBuilder(TransactionVolume transactionVolume) {
        TransactionVolumeBuilder builder = new TransactionVolumeBuilder();
        builder
            .user_id(transactionVolume.getUser_id())
            .amount(transactionVolume.getAmount())
            .count(transactionVolume.getCount());
        ;
        return builder;
    }

}
