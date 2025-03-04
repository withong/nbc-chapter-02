package calculator.level3.service;

import calculator.level3.exception.CalculatorException;
import calculator.level3.model.CalculationRecord;
import calculator.level3.model.Operand;
import calculator.level3.model.Operator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ArithmeticCalculator {

    private List<CalculationRecord> records;

    public ArithmeticCalculator() {
        records = new ArrayList<>();
    }

    public Operand<?> calculate(Operand<?> number1, Operator operator, Operand<?> number2) {
        try {
            BigDecimal result = operator.apply(number1, number2);
            return convertToResultType(result);

        } catch (CalculatorException e) {
            System.out.println(e.getMessage());
            return new Operand<>("정의되지 않음");
        }
    }

    private Operand<?> convertToResultType(BigDecimal result) {
        String toString = result.toString();
        BigDecimal bigDecimal = new BigDecimal(toString);
        bigDecimal = bigDecimal.setScale(8, RoundingMode.HALF_UP).stripTrailingZeros();

        // 과학적 표기법 적용 기준 확인
        boolean useScientificNotation = useScientificNotation(bigDecimal);

        String formatted;
        DecimalFormat scientificFormat = new DecimalFormat("0.######E0");
        DecimalFormat nonScientificFormat = new DecimalFormat("#.########");

        if (useScientificNotation) {
            formatted = scientificFormat.format(bigDecimal).replace("E", "e");
        } else {
            formatted = nonScientificFormat.format(bigDecimal); // 정수 & 일반 소수
        }

        return new Operand<>(formatted);
    }

    private boolean useScientificNotation(BigDecimal bigDecimal) {
        String plainString = bigDecimal.toPlainString();
        String[] parts = plainString.split("\\.");

        int integerPartLength = parts[0].length();
        int demicalPartLength = (parts.length > 1) ? parts[1].length() : 0;

        // 소수점 이상 10자리 or 소수점 이하 8자리 이상이면 과학적 표기법 적용
        return integerPartLength >= 10 || demicalPartLength >= 8;
    }
}
