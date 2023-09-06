import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class CustomerSerializer implements Serializer<Customer> {
    @Override
    public void configure(Map configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String s, Customer customer) {
        int id = customer.getId();
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        int age = customer.getAge();
        char gender = customer.getGender();
        double salary = customer.getSalary();

        try {
            byte[] serializedFirstName;
            int stringSize;

            if (customer == null) {
                return null;
            } else {
                if (customer.getFirstName() != null) {
                    serializedFirstName = customer.getFirstName().getBytes("UTF-8");
                    stringSize = serializedFirstName.length;
                } else {
                    serializedFirstName = new byte[0];
                    stringSize = 0;
                }
            }

            ByteBuffer buffer = ByteBuffer.allocate(4 + 4 + stringSize);
            buffer.putInt(customer.getId());
            buffer.putInt(stringSize);
            buffer.put(serializedFirstName);

            return buffer.array();
        } catch (Exception e) {
            throw new SerializationException("Error when serializing Customer");
        }
    }
}
