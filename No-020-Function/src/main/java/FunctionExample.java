import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FunctionExample {
    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    // Function接口
    public static void test1() {
        Function<String, Integer> function = String::length;
        System.out.println(function.apply("abc"));
    }

    // Function接口优化if else
    public static void test2() {
        Map<String, Function<String, Integer>> map = new HashMap();
        map.put("length", String::length);
        map.put("hashCode", String::hashCode);
        String str = "length";
        System.out.println(map.get(str).apply("abc"));
    }

    // 过滤，找出大于10的数字
    public static void test3() {
        int threshold = 10;
        Predicate<Integer> predicate = i -> i > threshold;
        List<Integer> collect = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
                .filter(predicate)
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
