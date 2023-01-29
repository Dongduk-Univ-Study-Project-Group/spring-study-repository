package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

// 컴포넌트 스캔을 사용하지 않고 직접 빈으로 등록해보자.
@Slf4j
public class FieldLogTrace implements LogTrace {
    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder;    // traceId 동기화 (동시성 이슈 발생)
    // 기존에는 파라미터에 전달했다면, 이제 해당 필드에 저장해놓고 사용한다.

    @Override
    public TraceStatus begin(String message) {
        syncTraceId();
        TraceId traceId = traceIdHolder;    // sync를 하고 나면 해당 필드에 값이 들어있음을 보장받음
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    private void syncTraceId() {
        if (traceIdHolder == null) {
            // 처음에 traceHolder가 null인 경우
            traceIdHolder = new TraceId();
        } else {
            // 처음이 아닌 경우
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
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

        releaseTraceId();
    }

    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) {   // 첫번째 레벨: 들어가다가 다시 나온거 (마지막, 즉 로그가 끝남)
            traceIdHolder = null;     // 파괴
        } else {
            // 아직 로그가 끝나지 않고 중간 단계임
            // 들어갔다가 나올 때 레벨이 하나씩 줄어든다.
            traceIdHolder = traceIdHolder.createPreviousId();
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
