package hello.advanced.trace;

public class TraceStatus {
    // 로그의 상태 정보를 나타낸다.
    // 로그를 종료할 땐 ID값과 로그를 시작할 때 사용했던 메세지, 시간이 필요하다.
    // 시작 시간을 알면 종료할 땐 시간을 계산할 수 있다.
    private TraceId traceId;
    private Long startTimeMs;   // 로그 시작 시간
    private String message;   // 시작 시 사용한 메세지, 로그 종료시에도 해당 메세지를 사용하여 출력

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
