package algorism.level1;

public class Code18 { // 문자열을 정수로 바꾸기

    public static void main(String[] args) {
        int solution = solution("-12345");
        System.out.println("solution = " + solution);
    }

    public static int solution(String s) {
        int answer = Integer.parseInt(s);
        return answer;
    }
}
