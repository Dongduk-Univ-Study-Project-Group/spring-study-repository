package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

class HelloTraceV2Test {
    @Test
    void begin_end() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.end(status2);   // 걸린 시간이 출력된다.
        trace.end(status1);   // 호출이 들어오고 나갈 때 순서에 맞춰 호출
    }

    @Test
    void begin_exception() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
        trace.exception(status2, new IllegalStateException());   // 걸린 시간이 출력된다.
        trace.exception(status1, new IllegalStateException());   // 호출이 들어오고 나갈 때 순서에 맞춰 호출
    }

    // 콘솔에 결과가 출력될 뿐 값이 리턴되는 것이 없기 때문에 응답 값이 없는 경우를 자동으로 검증하려면
    // 여러가지 테스트 기법이 필요하다.
}