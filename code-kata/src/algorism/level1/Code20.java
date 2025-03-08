package algorism.level1;

import java.util.Arrays;

public class Code20 {
/*
    함수 solution은 정수 n을 매개변수로 입력받습니다.
    n의 각 자릿수를 큰것부터 작은 순으로 정렬한 새로운 정수를 리턴해주세요.
    예를들어 n이 118372면 873211을 리턴하면 됩니다.
*/
    public static void main(String[] args) {
        long result = solution(118372);
        System.out.println("result = " + result);
    }

    public static long solution(long n) {
        char[] arr = String.valueOf(n).toCharArray();
        Arrays.sort(arr);

        StringBuilder stringBuilder = new StringBuilder(String.valueOf(arr));
        stringBuilder.reverse();

        return Long.parseLong(String.valueOf(stringBuilder));
    }

}
