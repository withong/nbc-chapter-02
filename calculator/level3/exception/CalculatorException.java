package calculator.level3.exception;

public class CalculatorException extends RuntimeException {

    public enum Type {
        EMPTY_VALUE, INVALID_NUMBER, INVALID_OPERATOR, DIVISION_BY_ZERO
    }

    private final Type type;

    public CalculatorException(Type type) {
        super();
        this.type = type;
    }

    @Override
    public String getMessage() {
        return switch (type) {
            case EMPTY_VALUE -> "입력된 값이 없습니다.";
            case INVALID_NUMBER -> "유효하지 않은 숫자입니다.";
            case INVALID_OPERATOR -> "유효하지 않은 연산자입니다";
            case DIVISION_BY_ZERO -> "0으로 나눌 수 없습니다.";
        };
    }
}
