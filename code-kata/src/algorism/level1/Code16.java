package algorism.level1;

import java.util.Arrays;

public class Code16 { // x부터 시작해 x씩 증가하는 숫자를 n개 지니는 리스트를 리턴

    public static void main(String[] args) {
        long[] solution = solution(2, 5);
        String result = Arrays.toString(solution);
        System.out.println("result = " + result);

    }

    public static long[] solution(int x, int n) {
        long[] answer = new long[n];

        for (int i = 0; i < n; i++) {
            answer[i] = (long) x * (i + 1);
        }
        return answer;
    }
}
