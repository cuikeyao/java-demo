import java.util.List;
import java.util.Map;
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
        test8();
        test9();
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

    // reduce归约
    public static void test8() {
        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9,10);
        Integer reduce = list.stream().reduce(0, (a, b) -> a + b);
        System.out.println(reduce);
    }

    // 分组
    public static void test9() {
        List<String> list = List.of("abc","fdsf","fsfk","fs","ose");
        Map<Integer, List<String>> collect = list.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(collect);
    }
}
