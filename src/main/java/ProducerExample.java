import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import scala.sys.Prop;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class ProducerExample {
    public ProducerExample() {
        Properties props = new Properties();
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "customer-producer");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CustomerSerializer.class.getName());

        KafkaProducer<String, Customer> producer = new KafkaProducer<>(props);

        String topic = "customers";

        for (int i = 0; i < 1000000; i++) {
            Customer customer = new Customer(i,
                    "first_name_"+i,
                    "last_name_"+i,
                    (int)new Random().nextGaussian()*(60-18+1)+18,
                    (new Random().nextBoolean()) ?'F':'M',
                    (new Random().nextDouble()) * (50000 - 2600 + 1) + 2600
            );

            String key = UUID.randomUUID().toString();

            ProducerRecord<String, Customer> record = new ProducerRecord<>(topic, key, customer);
            producer.send(record);
        }
    }

    public static void main(String[] args) {
        new ProducerExample();
    }
}
