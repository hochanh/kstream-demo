package app;

import lombok.extern.java.Log;
import model.PaymentTransaction;
import model.TransactionVolume;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.kstream.*;
import util.serde.StreamsSerdes;

import java.time.Duration;
import java.util.Properties;



@Log
public class KafkaStreamApp {

    public static final String PAYMENT_POST_TRANSACTION_TOPIC = "events";

    public static void main(String[] args) {

        Serde<String> stringSerde = Serdes.String();
        Serde<PaymentTransaction> paymentTransactionSerde  = new StreamsSerdes.PaymentTransactionSerde();
        Serde<TransactionVolume> transactionVolumeSerde  = new StreamsSerdes.TransactionVolumeSerde();

        StreamsConfig streamsConfig = new StreamsConfig(getProperties());
        StreamsBuilder builder = new StreamsBuilder();

        KTable<Windowed<TransactionVolume>, Long> transactionCounts = builder
                .stream(PAYMENT_POST_TRANSACTION_TOPIC, Consumed.with(stringSerde, paymentTransactionSerde)
                        .withOffsetResetPolicy(Topology.AutoOffsetReset.LATEST))
                .groupBy((k, v) -> TransactionVolume.newBuilder(v).build(), Serialized.with(transactionVolumeSerde, paymentTransactionSerde))
                .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                .count();

        transactionCounts.toStream().peek((k, v) -> log.info("User id: " + k.key().getUser_id() + " Count: " + v));

        log.info("Starting example...");

        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), streamsConfig);
        kafkaStreams.start();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "kstream_application");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        properties.put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class);
        return properties;
    }
}
