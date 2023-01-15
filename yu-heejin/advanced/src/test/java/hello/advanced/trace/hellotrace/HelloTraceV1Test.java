package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV1Test {
    @Test
    void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);   // 걸린 시간이 출력된다.
    }

    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

    // 콘솔에 결과가 출력될 뿐 값이 리턴되는 것이 없기 때문에 응답 값이 없는 경우를 자동으로 검증하려면
    // 여러가지 테스트 기법이 필요하다.
}