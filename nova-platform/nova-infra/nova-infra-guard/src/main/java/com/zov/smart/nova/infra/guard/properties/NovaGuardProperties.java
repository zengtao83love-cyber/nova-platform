package com.zov.smart.nova.infra.guard.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Guard infrastructure configuration.
 */
@ConfigurationProperties(prefix = "nova.guard")
public class NovaGuardProperties {

    /** Whether repeat-submit guard aspect is enabled. */
    private boolean repeatSubmitEnabled = true;

    /** Whether resource-lock guard aspect is enabled. */
    private boolean resourceLockEnabled = true;

    /** Redis key prefix for repeat-submit windows. */
    private String repeatSubmitPrefix = "nova:guard:repeat-submit:";

    /** Redis key prefix for resource locks. */
    private String resourceLockPrefix = "nova:guard:resource-lock:";

    public boolean isRepeatSubmitEnabled() {
        return repeatSubmitEnabled;
    }

    public void setRepeatSubmitEnabled(boolean repeatSubmitEnabled) {
        this.repeatSubmitEnabled = repeatSubmitEnabled;
    }

    public boolean isResourceLockEnabled() {
        return resourceLockEnabled;
    }

    public void setResourceLockEnabled(boolean resourceLockEnabled) {
        this.resourceLockEnabled = resourceLockEnabled;
    }

    public String getRepeatSubmitPrefix() {
        return repeatSubmitPrefix;
    }

    public void setRepeatSubmitPrefix(String repeatSubmitPrefix) {
        this.repeatSubmitPrefix = repeatSubmitPrefix;
    }

    public String getResourceLockPrefix() {
        return resourceLockPrefix;
    }

    public void setResourceLockPrefix(String resourceLockPrefix) {
        this.resourceLockPrefix = resourceLockPrefix;
    }
}
