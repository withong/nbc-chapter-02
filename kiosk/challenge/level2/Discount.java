package challenge.level2;

public enum Discount {
    BIRTHDAY("생일",0.2),
    REVIEW("리뷰 작성", 0.1),
    SNS("SNS 업로드", 0.1),
    STUDENT("학생", 0.05);

    private final String name;
    private final double rate;

    Discount(String name, double rate) {
        this.name = name;
        this.rate = rate;
    }

    public int getDiscounted(int price) {
        return (int) (price - (price * rate));
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
