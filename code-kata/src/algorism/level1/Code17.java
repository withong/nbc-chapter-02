package algorism.level1;

import java.util.Arrays;

public class Code17 {

    public static void main(String[] args) { // 자연수 뒤집어 배열로 만들기

        int[] solution = solution(12345);
        System.out.println("solution = " + Arrays.toString(solution));
    }

    public static int[] solution(long num) {
        String str = String.valueOf(num);
        int[] answer = new int[str.length()];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = str.charAt(str.length() - 1 - i) - '0';
        }
        return answer;
    }
}
