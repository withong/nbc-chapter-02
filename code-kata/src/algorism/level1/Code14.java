package algorism.level1;

public class Code14 { // 약수의 합

    public static void main(String[] args) {
        int answer = 0;

        int num = 100;
        int sqrt = (int) Math.sqrt(num);

        for (int i = 1; i <= sqrt; i++) {
            if (num % i == 0) {
                answer += i;
                if (i != (num / i)) {
                    answer += num/i;
                }
            }
        }
        System.out.println("answer = "+answer);
    }
}

