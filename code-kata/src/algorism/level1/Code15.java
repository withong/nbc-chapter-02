package algorism.level1;

public class Code15 { // 나머지가 1이 되도록 하는 가장 작은 자연수 찾기

    public static void main(String[] args) {
        int answer = 0;
        int number = 12;

        for (int i = 1; i < number; i++) {
            if (number % i == 1) {
                answer = i;
                break;
            }
        }
        System.out.println("answer = " + answer);
    }
}
