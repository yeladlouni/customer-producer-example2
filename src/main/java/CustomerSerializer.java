import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class CustomerSerializer implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, Object o) {
        Customer customer = (Customer) o;
        int id = customer.getId();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        int age = customer.getAge();
        char gender = customer.getGender();
        double salary = customer.getSalary();

        int bytesSize = 4 + (firstName.length() + lastName.length()) * 8 + 4 + 8 + 16;
        byte[] bytes = new byte[bytesSize];

        return new byte[0];
    }

    @Override
    public byte[] serialize(String topic, Headers headers, Object data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
