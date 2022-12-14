/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.dema.store.common.data.api.interceptors

import com.dema.store.common.data.api.ApiParameters
import com.dema.store.common.data.api.ApiParameters.AUTH_HEADER
import com.dema.store.common.data.api.ApiParameters.NO_AUTH_HEADER
import com.dema.store.common.data.api.ApiParameters.TOKEN_TYPE
import com.dema.store.common.data.api.model.ApiAuthenticator
import com.dema.store.common.data.preferences.Preferences
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.threeten.bp.Instant
import javax.inject.Inject

class AuthenticationInterceptor @Inject constructor(
    private val preferences: Preferences
) : Interceptor {

    companion object {
        const val UNAUTHORIZED = 401
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.getToken() // 1
        val request = chain.request() // 3
        if (chain.request().headers[NO_AUTH_HEADER] != null) {
            val interceptedRequest = chain.proceed(request)
            if (chain.request().headers[ApiParameters.CREATING_TOKEN] != null){
                if (interceptedRequest.isSuccessful){
                    val newToken = mapToken(interceptedRequest)
                    if (newToken.isValid()) {
                        storeNewToken(newToken)
                    }
                }
            }
                return interceptedRequest
        } // 4
        val interceptedRequest: Request = chain.createAuthenticatedRequest(token) // 1 // 2

        return chain
            .proceedDeletingTokenIfUnauthorized(interceptedRequest) // 3
    }

    private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request {
        return request()
            .newBuilder()
            .addHeader(AUTH_HEADER, TOKEN_TYPE + token)
            .build()
    }


    private fun mapToken(tokenRefreshResponse: Response): ApiAuthenticator {
        val moshi = Moshi.Builder().build()
        val tokenAdapter = moshi.adapter(ApiAuthenticator::class.java)
        val responseBody = tokenRefreshResponse.body!! // if successful, this should be good :]

        return tokenAdapter.fromJson(responseBody.string()) ?: ApiAuthenticator.INVALID
    }

    private fun storeNewToken(apiToken: ApiAuthenticator) {
        with(preferences) {
            putToken(apiToken.token!!)
        }
    }

    private fun Interceptor.Chain.proceedDeletingTokenIfUnauthorized(request: Request): Response {
        val response = proceed(request)
        val code = response.code
        if (code == UNAUTHORIZED) {
            preferences.deleteTokenInfo()
        }

        return response
    }

}
