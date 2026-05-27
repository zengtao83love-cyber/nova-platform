package com.zov.smart.nova.system.api.event;

import java.io.Serializable;

/**
 * Stable event contract for system-cache invalidation.
 *
 * <p>This is a pure data carrier. It has no Spring dependency and can be used by
 * future application-event implementations without coupling API to infra.</p>
 */
public class SystemCacheEvictEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String cacheName;
    private String cacheKey;
    private String reason;

    public String getCacheName() { return cacheName; }
    public void setCacheName(String cacheName) { this.cacheName = cacheName; }
    public String getCacheKey() { return cacheKey; }
    public void setCacheKey(String cacheKey) { this.cacheKey = cacheKey; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
