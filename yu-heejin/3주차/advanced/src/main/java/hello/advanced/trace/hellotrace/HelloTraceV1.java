package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component   // 싱글톤으로 사용하기 위해 스프링 빈으로 등록한다. 컴포넌트 스캔의 대상이 된다.
// 싱글톤 패턴을 직접 사용해도 되지만, 스프링 빈으로 등록하면 기본으로 싱글톤으로 사용할 수 있다.
public class HelloTraceV1 {
    // 싱글톤 사용
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String message) {
        // 로그를 시작한다.
        // 로그 메시지를 파라미터로 받아 시작 로그를 출력한다.
        // 응답 결과로 현재 로그의 상태인 TraceStatus를 반환한다.
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    public void end(TraceStatus status) {
        // 로그를 정상 종료한다.
        // 파라미터로 시작 로그의 상태(TraceStatus)를 전달받는다.
        // 이 값을 활용해서 실행 시간을 계산하고, 종료 시에도 시작할 때와 동일한 로그 메시지를 출력할 수 있다.
        // 정상 흐름에서 호출한다.
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e) {
        // 로그를 예외 상황으로 종료한다.
        // TraceStatus, Exception 정보를 함께 전달 받아서 실행 시간, 예외 정보를 포함한 결과 로그를 출력한다.
        // 예외 발생 시 호출한다.
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        // end(), exception()의 요청 흐름을 한 곳에서 편리하게 처리한다.
        // 실행 시간을 측정하고 로그를 남긴다.
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();

        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    private static String addSpace(String prefix, int level) {   // 화살표 추가
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < level; i++) {
            sb.append( (i == level - 1) ? "|" + prefix : "|   ");
        }

        return sb.toString();
    }
}
