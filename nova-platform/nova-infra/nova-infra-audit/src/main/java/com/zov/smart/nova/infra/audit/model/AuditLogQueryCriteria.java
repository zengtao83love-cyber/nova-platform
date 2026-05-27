package com.zov.smart.nova.infra.audit.model;

import com.zov.smart.nova.infra.audit.enums.AuditOperationTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Query criteria for audit log lookup. Kept inside infra-audit so nova-data-access
 * remains unrelated to audit storage.
 */
public class AuditLogQueryCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private String traceId;
    private String operationName;
    private AuditOperationTypeEnum operationType;
    private Integer successFlag;
    private Long operatorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public AuditOperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(AuditOperationTypeEnum operationType) {
        this.operationType = operationType;
    }

    public Integer getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Integer successFlag) {
        this.successFlag = successFlag;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
