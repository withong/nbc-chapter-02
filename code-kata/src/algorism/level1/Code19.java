package algorism.level1;

public class Code19 {
    /*
        임의의 양의 정수 n에 대해, n이 어떤 양의 정수 x의 제곱인지 아닌지 판단하려 합니다.
        n이 양의 정수 x의 제곱이라면 x+1의 제곱을 리턴하고, n이 양의 정수 x의 제곱이 아니라면 -1을 리턴
    */
    public static void main(String[] args) {
        long result = solution(121);
        System.out.println("result = " + result);
    }

    public static long solution(long n) {
        long sqrt = (long) Math.sqrt(n);

        if (sqrt * sqrt == n) {
            return (sqrt + 1) * (sqrt + 1);
        } else {
            return -1;
        }
    }
}
