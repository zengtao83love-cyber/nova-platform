package com.zov.smart.nova.infra.security.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

public class PermissionCodeResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Set<String> permissions = new LinkedHashSet<String>();
    public PermissionCodeResponse() {}
    public PermissionCodeResponse(Set<String> permissions) { setPermissions(permissions); }
    public Set<String> getPermissions() { return permissions; }
    public void setPermissions(Set<String> permissions) { this.permissions = permissions == null ? new LinkedHashSet<String>() : new LinkedHashSet<String>(permissions); }
}
