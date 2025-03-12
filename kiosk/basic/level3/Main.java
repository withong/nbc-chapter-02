package basic.level3;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<MenuItem> menuItems = Arrays.asList(
                new MenuItem("불고기 버거", 4200, "불고기 소스에 패티와 마요네즈, 양상추의 맛있는 조합."),
                new MenuItem("치즈 버거", 3600, "맛있는 치즈와 100% 순 쇠고기 패티, 클래식 치즈 버거."),
                new MenuItem("슈비 버거", 6600, "탱글한 통새우살에 비프 패티를 더해 푸짐한 슈비 버거."),
                new MenuItem("슈슈 버거", 5500, "탱글한 통새우살이 가득한 슈슈 버거.")
        );

        Kiosk kiosk = new Kiosk(menuItems);
        kiosk.start();
    }

}
