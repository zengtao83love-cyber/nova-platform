package com.zov.smart.nova.common.core.error;

import java.io.Serializable;

/**
 * Unified business error code contract.
 *
 * <p>The {@code code} field is returned in {@code Result.code}; {@code httpStatus}
 * is consumed by web exception mapping and must not be mixed with business code.
 * This keeps the SDD error-code matrix explicit and stable.</p>
 */
public interface ErrorCode extends Serializable {

    /**
     * Machine-readable business code, for example {@code COMMON_PARAM_INVALID}.
     *
     * @return stable business error code
     */
    String getCode();

    /**
     * User-facing message that does not leak internal implementation details.
     *
     * @return display message
     */
    String getMessage();

    /**
     * HTTP status that should be used by the web layer when this error is returned.
     *
     * @return HTTP status code, for example 400, 401, 403, 404, 409, 500, or 200 for expected business rules
     */
    int getHttpStatus();
}
