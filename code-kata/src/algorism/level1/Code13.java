package algorism.level1;

import java.util.Arrays;

public class Code13 { // 자릿수 더하기

    public static void main(String[] args) {
        int result = solution(12345);
        System.out.println("result = " + result);
    }

    public static int solution(int num) {
        int answer = Arrays.stream(String.valueOf(num).split(""))
                .mapToInt(Integer::parseInt)
                .sum();

        return answer;
    }
}

