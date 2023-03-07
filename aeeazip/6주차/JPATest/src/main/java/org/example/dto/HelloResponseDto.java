package org.example.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // 모든 필드 포함된 생성장 생성
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
