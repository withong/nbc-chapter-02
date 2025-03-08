package level3.service;

import level3.exception.CalculatorException;
import level3.model.CalculationRecord;
import level3.model.Operand;
import level3.model.Operator;

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

            Operand<?> formatted = formatOperand(result);
            updateRecord(number1, operator, number2, formatted);

            return formatted;

        } catch (CalculatorException e) {
            System.out.println(e.getMessage());

            Operand<?> result = new Operand<>("정의되지 않음");
            updateRecord(number1, operator, number2, result);

            return result;
        }
    }

    public List<CalculationRecord> getRecords() {
        return records;
    }

    public void removeRecord(int index) {
        records.remove(index);
    }

    public void clearRecords() {
        records.clear();
    }

    private void updateRecord(Operand<?> number1, Operator operator, Operand<?> number2, Operand<?> result) {
        records.add(new CalculationRecord(number1, operator, number2, result));
    }

    private Operand<?> formatOperand(BigDecimal result) {
        BigDecimal bigDecimal = result.setScale(8, RoundingMode.HALF_UP).stripTrailingZeros();

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
        int decimalPartLength = (parts.length > 1) ? parts[1].length() : 0;

        // 소수점 이상 10자리 or 소수점 이하 8자리 이상이면 과학적 표기법 적용
        return integerPartLength >= 10 || decimalPartLength >= 8;
    }
}
