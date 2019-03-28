### Setup Kafka, MySQL

```
docker-compose up
```

### Run Kstream

```
./gradlew runPOC
```

### Publish events

```sh
cd /opt/kafka

bin/kafka-topics.sh --describe --zookeeper zookeeper:2181 --topic events
bin/kafka-console-producer.sh --broker-list kafka:9092 --topic events
bin/kafka-console-consumer.sh --bootstrap-server kafka:9092 --topic events
```

### Sample

```
{"user_id": "1", "transaction_amount": 300}
```

Enjoy!
