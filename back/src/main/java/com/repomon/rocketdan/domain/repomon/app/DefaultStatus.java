package com.repomon.rocketdan.domain.repomon.app;

import lombok.Getter;

@Getter
public class DefaultStatus {
    // 기본값
    public static final Integer defaultAtk = 20;
    public static final Float defaultDodge = 20f;
    public static final Float defaultDef = 0f;
    public static final Float defaultCritical = 10f;
    public static final Float defaultHit = 20f;
    public static final Integer defaultHp = 100;

    // 증가치
    public static final Integer atkValue = 2;
    public static final Float dodgeValue = 1f;
    public static final Float defValue = 1f;
    public static final Float criticalValue = 1f;
    public static final Float hitValue = 1f;
    public static final Float hpValue = 0.05f;
}
