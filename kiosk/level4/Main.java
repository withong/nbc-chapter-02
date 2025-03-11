package level4;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Menu> menu = Arrays.asList(
                new Menu("햄버거").addMenuItems(
                        new MenuItem("불고기 버거", 4200, "불고기 소스에 패티와 마요네즈, 양상추의 맛있는 조합."),
                        new MenuItem("치즈 버거", 3600, "맛있는 치즈와 100% 순 쇠고기 패티, 클래식 치즈 버거."),
                        new MenuItem("슈비 버거", 6600, "탱글한 통새우살에 비프 패티를 더해 푸짐한 슈비 버거."),
                        new MenuItem("슈슈 버거", 5500, "탱글한 통새우살이 가득한 슈슈 버거.")
                ),
                new Menu("음료").addMenuItems(
                        new MenuItem("코카콜라", 2600, "갈증 해소뿐만이 아니라 기분까지 상쾌하게"),
                        new MenuItem("스프라이트", 2600, "청량함에 레몬, 라임향을 더한 시원함"),
                        new MenuItem("아메리카노", 3300, "바로 내린 100% 친환경 커피로 더 신선하게")
                ),
                new Menu("사이드").addMenuItems(
                        new MenuItem("후렌치 후라이", 3000, "남다른 맛과 바삭함"),
                        new MenuItem("코울슬로", 2700, "아삭하게 씹히는 샐러드"),
                        new MenuItem("치즈스틱", 3600, "속이 꽉 찬 황금빛 바삭함")
                )
        );

        Kiosk kiosk = new Kiosk(menu);
        kiosk.start();
    }
}
