import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
    }

    // 转换为另一种集合
    public static void test1() {
        List<String> list = List.of("a", "b", "c");
        List<String> collect = list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(collect);
    }
    // 获取集合中的第一个元素
    public static void test2() {
        List<String> list = List.of("a", "b", "c");
        String first = list.stream()
                .findFirst()
                .orElse("");
        System.out.println(first);
    }

    // filter过滤
    public static void test3() {
        List<String> list = List.of("aa", "bbb", "cc");
        List<String> collect = list.stream()
                .filter(s -> s.length() == 2)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    // filter过滤
    public static void test4() {
        List<String> list = List.of("aa", "bbb", "cc");
        List<String> collect = list.stream()
                .filter(s -> s.length() == 2)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    // 将多个流合并为一个流
    public static void test5() {
        Stream<List<String>> lists = Stream.of(List.of("A", "B"), List.of("C", "D"));
        List<String> collect = lists.flatMap(list -> list.stream()).collect(Collectors.toList());
        System.out.println(collect);
    }

    // 集合排序
    public static void test6() {
        List<String> list = List.of("b", "c", "a", "d");

        List<String> collect1 = list.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(collect1);

        List<String> collect2 = list.stream()
                .sorted((s1, s2) -> s2.compareTo(s1))
                .collect(Collectors.toList());
        System.out.println(collect2);
    }

    // 去重
    public static void test7() {
        List<String> list = List.of("a", "b", "c", "a", "b", "c");
        List<String> collect = list.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);
    }
}
