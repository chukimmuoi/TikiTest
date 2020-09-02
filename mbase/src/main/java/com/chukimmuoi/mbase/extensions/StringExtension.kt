package com.chukimmuoi.mbase.extensions

import java.security.MessageDigest
import java.util.regex.Pattern

/**
 * @author  : Chu Kim Muoi
 * @Skype   : chukimmuoi
 * @Mobile  : +84 373 672 505
 * @Email   : chukimmuoi@gmail.com
 * @Project : accesstrade
 * Created by chukimmuoi on 2019-11-24.
 */

const val EMAIL_PATTERN =
    "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"

// used for validate if the current String is an email
fun String.isValidEmail(): Boolean {
    val pattern = Pattern.compile(EMAIL_PATTERN)
    return pattern.matcher(this).matches()
}

private enum class HashType {
    MD5, SHA1, SHA256
}

fun String.applyMD5(): String = this.hashWithAlgorithm(HashType.MD5)

fun String.applySHA1(): String = this.hashWithAlgorithm(HashType.SHA1)

fun String.applySHA256(): String = this.hashWithAlgorithm(HashType.SHA256)

private fun String.hashWithAlgorithm(type: HashType): String {
    return MessageDigest.getInstance(type.name)
        .digest(this.toByteArray(Charsets.UTF_8))
        .joinToString(separator = "") { String.format("%02x", it) }
}

