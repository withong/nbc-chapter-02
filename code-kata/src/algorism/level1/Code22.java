package algorism.level1;

public class Code22 {
/*
    두 정수 a, b가 주어졌을 때 a와 b 사이에 속한 모든 정수의 합을 리턴하는 함수, solution을 완성하세요.
    예를 들어 a = 3, b = 5인 경우, 3 + 4 + 5 = 12이므로 12를 리턴합니다.
*/
    public static void main(String[] args) {
        long result = solution(5, 3);
        System.out.println("result = " + result);
    }

    public static long solution(int a, int b) {
        int start = Math.min(a, b);  // 작은 값
        int end = Math.max(a, b);

        return (long) (end - start + 1) * (start + end) / 2;
    }
}
