import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDateTime;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        LocalDateTime currentDateTime = DateUtils.getCurrentDateTime();
        System.out.println(DateUtils.getCurrentDateTime());
        System.out.println(DateUtils.formatLocalDate(currentDateTime.toLocalDate(), "yyyy-MM-dd"));
        System.out.println(DateUtils.getAge(currentDateTime.toLocalDate()));
        System.out.println(DateUtils.getDayOfMonth(currentDateTime.toLocalDate()));
        System.out.println(DateUtils.getDayOfWeek(currentDateTime.toLocalDate()));
        System.out.println(DateUtils.getDaysBetween(currentDateTime.toLocalDate(), DateUtils.addDays(currentDateTime.toLocalDate(), 10)));
        System.out.println(DateUtils.getDaysInMonth(2023, 2));
        System.out.println(DateUtils.getFirstDayOfMonth(currentDateTime.toLocalDate()));

        // hutool 工具包
        Date date = DateUtil.date();
        System.out.println(DateUtil.today());
        System.out.println(DateUtil.ageOfNow("1990-01-01"));
        System.out.println(DateUtil.dayOfMonth(date));
        System.out.println(DateUtil.dayOfWeek(date));
        System.out.println(DateUtil.between(date, DateUtil.offsetDay(date, 10), DateUnit.DAY));
        System.out.println(DateUtil.month(date));
        System.out.println(DateUtil.dayOfWeekEnum(date));
        System.out.println(DateUtil.dayOfWeek(DateUtil.offsetDay(date, 1)));
    }
}
