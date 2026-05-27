package com.zov.smart.nova.infra.mybatisplus.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MyBatis-Plus runtime configuration properties.
 *
 * <p>Only declares {@code nova.mybatis-plus.*} binding metadata. Physical values must be
 * provided by {@code nova-bootstrap/src/main/resources/application.yml}; infra modules must
 * not ship application.yml or application.properties.</p>
 */
@ConfigurationProperties(prefix = "nova.mybatis-plus")
public class NovaMybatisPlusProperties {

    private Pagination pagination = new Pagination();
    private OptimisticLocker optimisticLocker = new OptimisticLocker();
    private BlockAttack blockAttack = new BlockAttack();
    private AuditFill auditFill = new AuditFill();

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination == null ? new Pagination() : pagination;
    }

    public OptimisticLocker getOptimisticLocker() {
        return optimisticLocker;
    }

    public void setOptimisticLocker(OptimisticLocker optimisticLocker) {
        this.optimisticLocker = optimisticLocker == null ? new OptimisticLocker() : optimisticLocker;
    }

    public BlockAttack getBlockAttack() {
        return blockAttack;
    }

    public void setBlockAttack(BlockAttack blockAttack) {
        this.blockAttack = blockAttack == null ? new BlockAttack() : blockAttack;
    }

    public AuditFill getAuditFill() {
        return auditFill;
    }

    public void setAuditFill(AuditFill auditFill) {
        this.auditFill = auditFill == null ? new AuditFill() : auditFill;
    }

    public static class Pagination {
        private Boolean enabled = Boolean.TRUE;
        private Long maxLimit = 500L;
        private Boolean overflow = Boolean.FALSE;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled == null ? Boolean.TRUE : enabled;
        }

        public Long getMaxLimit() {
            return maxLimit;
        }

        public void setMaxLimit(Long maxLimit) {
            this.maxLimit = maxLimit == null || maxLimit <= 0 ? 500L : maxLimit;
        }

        public Boolean getOverflow() {
            return overflow;
        }

        public void setOverflow(Boolean overflow) {
            this.overflow = overflow == null ? Boolean.FALSE : overflow;
        }

        public boolean isEnabled() {
            return Boolean.TRUE.equals(enabled);
        }

        public boolean isOverflow() {
            return Boolean.TRUE.equals(overflow);
        }
    }

    public static class OptimisticLocker {
        private Boolean enabled = Boolean.TRUE;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled == null ? Boolean.TRUE : enabled;
        }

        public boolean isEnabled() {
            return Boolean.TRUE.equals(enabled);
        }
    }

    public static class BlockAttack {
        private Boolean enabled = Boolean.TRUE;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled == null ? Boolean.TRUE : enabled;
        }

        public boolean isEnabled() {
            return Boolean.TRUE.equals(enabled);
        }
    }

    public static class AuditFill {
        private Boolean enabled = Boolean.TRUE;

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled == null ? Boolean.TRUE : enabled;
        }

        public boolean isEnabled() {
            return Boolean.TRUE.equals(enabled);
        }
    }
}
