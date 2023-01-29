package chapter03.used;

/**
 * Calculator 구현 클래스
 * 뺄셈 처리
 */
public class SubCalc implements Calculator {
    @Override
    public Integer calc(Integer x, Integer y) {
        return x - y;
    }
}
