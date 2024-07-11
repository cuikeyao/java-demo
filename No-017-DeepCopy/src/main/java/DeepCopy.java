import org.apache.commons.lang3.SerializationUtils;

public class DeepCopy {
    public static void main(String[] args) {
        Address address = new Address("xian", "gaoxin");
        Person person = new Person("Keeyo", 18, address);
        System.out.println(person);
        System.out.println(person.getAddress());

        Person person1 = SerializationUtils.clone(person);
        System.out.println(person1);
        System.out.println(person1.getAddress());
    }
}
