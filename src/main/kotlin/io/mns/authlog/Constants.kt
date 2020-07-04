package io.mns.authlog

object SecurityConstants {
    const val SECRET = "zbi8mhIPczkEliKt63OK"
    const val EXPIRATION_TIME: Long = 864000000 // 10 days
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val EXPOSE_HEADERS = "Access-Control-Expose-Headers"
    const val SIGN_UP_URL = "/users/sign-up"
}

const val MAX_LOG_COUNT = 5