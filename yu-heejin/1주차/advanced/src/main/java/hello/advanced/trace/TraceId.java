package hello.advanced.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {
    private String id;    // 트랜잭션 ID
    private int level;    // 메소드 호출 레벨

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
        // 생성된 UUID는 매우 긴 문자열이다. 따라서 잘라서 쓰는 것이 좋다.
        // 앞에 8자리만 사용한다.
    }

    public TraceId createNextId() {
        // 다음 아이디 생성
        // trace id는 같고, 레벨이 하나 증가해야하는 메소드 호출에 사용한다.
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousId() {
        // 이전 아이디 생성
        return new TraceId(id, level - 1);
    }

    public boolean isFirstLevel() {   // 첫번째 레벨인가?
        return level == 0;
    }
}
