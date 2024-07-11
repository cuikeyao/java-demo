import bean.Address;
import bean.UserInfo;

import java.util.List;
import java.util.Optional;

public class OptionalExample {
    public static void main(String[] args) {
        Address address = new Address("Beijing", "datong");
        UserInfo userInfo = new UserInfo("Keeyo", 13, address);
        test1(List.of(userInfo));
        test2(new UserInfo("Keeyo", 13, address));
    }

    //遍历打印 userList
    public static void test1(List<UserInfo> userList) {
        for (UserInfo userInfo : Optional.ofNullable(userList)
                .orElse(List.of())) {
            //print userInfo
            System.out.println(userInfo);
        }
    }

    //Optional判空
    public static void test2(UserInfo userInfo) {
        String city = Optional.ofNullable(userInfo)
                .map(UserInfo::getAddress)
                .map(Address::getCity)
                .orElse("");

        String city1 = Optional.ofNullable(userInfo)
                .map(UserInfo::getAddress)
                .map(Address::getCity)
                .orElseThrow(() -> new IllegalStateException("user or address is null"));

        String city3 = Optional.ofNullable(userInfo)
                .map(UserInfo::getAddress)
                .map(Address::getCity)
                .get();

        Optional.ofNullable(userInfo)
                .map(UserInfo::getAddress)
                .map(Address::getCity)
                .ifPresent(System.out::println);

    }
}
