package com.plcoding.bookpedia.core.data

import com.plcoding.bookpedia.core.domain.DataError
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlin.test.Test
import kotlin.test.assertEquals
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.test.runTest

class HttpClientExtSafeCallTest {

    @Test
    fun `safeCall should return REQUEST_TIMEOUT when SocketTimeoutException is thrown`() = runTest {
        val result = safeCall<Unit> {
            throw SocketTimeoutException("Timeout")
        }
        assertEquals(Result.Error(DataError.Remote.REQUEST_TIMEOUT), result)
    }

    @Test
    fun `safeCall should return NO_INTERNET when UnresolvedAddressException is thrown`() = runTest {
        val result = safeCall<Unit> {
            throw UnresolvedAddressException()
        }
        assertEquals(Result.Error(DataError.Remote.NO_INTERNET), result)
    }

    @Test
    fun `safeCall should return UNKNOWN when an unexpected exception is thrown`() = runTest {
        val result = safeCall<Unit> {
            throw Exception("Some error")
        }
        assertEquals(Result.Error(DataError.Remote.UNKNOWN), result)
    }

}