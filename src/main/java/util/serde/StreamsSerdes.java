package util.serde;

import model.PaymentTransaction;
import model.TransactionVolume;
import org.apache.kafka.common.serialization.Serdes;
import util.serializer.JsonDeserializer;
import util.serializer.JsonSerializer;

public class StreamsSerdes {

    public static final class PaymentTransactionSerde extends Serdes.WrapperSerde<PaymentTransaction> {
        public PaymentTransactionSerde(){
            super(new JsonSerializer<>(), new JsonDeserializer<>(PaymentTransaction.class));
        }
    }


    public static final class TransactionVolumeSerde extends Serdes.WrapperSerde<TransactionVolume> {
        public TransactionVolumeSerde(){
            super(new JsonSerializer<>(), new JsonDeserializer<>(TransactionVolume.class));
        }
    }
}
