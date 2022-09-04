package com.dema.store.data.interceptors

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dema.store.common.data.api.ApiConstants
import com.dema.store.common.data.api.ApiParameters
import com.dema.store.common.data.api.interceptors.AuthenticationInterceptor
import com.dema.store.common.data.preferences.Preferences
import com.dema.store.data.utils.JsonReader
import com.google.common.truth.Truth.assertThat
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*


@RunWith(AndroidJUnit4::class)
class AuthenticationInterceptorTest {

    private lateinit var preferences: Preferences
    private lateinit var mockWebServer: MockWebServer
    private lateinit var authenticationInterceptor: AuthenticationInterceptor
    private lateinit var okHttpClient: OkHttpClient

    private val endpointSeparator = "/"
    private val productsEndpointPath = endpointSeparator + ApiConstants.PRODUCTS_ENDPOINT
    private val authEndpointPath = endpointSeparator + ApiConstants.AUTH_ENDPOINT
    private val validToken = "validToken"
    private val expiredToken = "expiredToken"

    var formBody: RequestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("somParam", "someValue")
        .build()
    @Before
    fun setup() {
        preferences = mock(Preferences::class.java)

        mockWebServer = MockWebServer()
        mockWebServer.start(8080)

        authenticationInterceptor = AuthenticationInterceptor(preferences)
        okHttpClient = OkHttpClient().newBuilder().addInterceptor(authenticationInterceptor).build()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun authenticationInterceptor_validToken() {
        //Given
        `when`(preferences.getToken()).thenReturn(validToken)

        mockWebServer.dispatcher = getDispatcherForValidToken()

        // When
        okHttpClient.newCall(
            Request.Builder().url(mockWebServer.url(ApiConstants.PRODUCTS_ENDPOINT)).build()
        ).execute()

        // Then
        val request = mockWebServer.takeRequest()

        with(request) {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(productsEndpointPath)
            assertThat(getHeader(ApiParameters.AUTH_HEADER)).isEqualTo(ApiParameters.TOKEN_TYPE + validToken)
        }
    }

    @Test
    fun authenticationInterceptor_unauthorized() {
        //Given
        `when`(preferences.getToken()).thenReturn(expiredToken)

        mockWebServer.dispatcher = getDispatcherForExpiredToken()

        // When
        okHttpClient.newCall(
            Request.Builder().url(mockWebServer.url(ApiConstants.PRODUCTS_ENDPOINT)).build()
        ).execute()

        // Then
        val request = mockWebServer.takeRequest()

        val inOrder = inOrder(preferences)

        inOrder.verify(preferences).getToken()
        inOrder.verify(preferences).deleteTokenInfo()

        verify(preferences, times(1)).getToken()
        verify(preferences, times(1)).deleteTokenInfo()
        verifyNoMoreInteractions(preferences)
    }

    @Test
    fun authenticatorInterceptor_expiredToken() {
        // Given
        `when`(preferences.getToken()).thenReturn(expiredToken)

        mockWebServer.dispatcher = getDispatcherForExpiredToken()

        // When
        okHttpClient.newCall(
            Request.Builder().url(mockWebServer.url(ApiConstants.AUTH_ENDPOINT)).header(ApiParameters.NO_AUTH_HEADER,"no_auth").post(formBody).header(ApiParameters.CREATING_TOKEN,"Creating").build()
        ).execute()

        // Then
        val tokenRequest = mockWebServer.takeRequest()

        with(tokenRequest) {
            assertThat(method).isEqualTo("POST")
            assertThat(path).isEqualTo(authEndpointPath)
        }

        val inOrder = inOrder(preferences)

        inOrder.verify(preferences).getToken()
        inOrder.verify(preferences).putToken(validToken)

        verify(preferences, times(1)).getToken()
        verify(preferences, times(1)).putToken(validToken)
        verifyNoMoreInteractions(preferences)

    }

    private fun getDispatcherForValidToken() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                productsEndpointPath -> {
                    MockResponse().setResponseCode(200)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }

    private fun getDispatcherForExpiredToken() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                authEndpointPath -> {
                    MockResponse().setResponseCode(200)
                        .setBody(JsonReader.getJson("loginResponse.json"))
                }
                productsEndpointPath -> {
                    MockResponse().setResponseCode(401)
                }
                else -> {
                    MockResponse().setResponseCode(404)
                }
            }
        }
    }
}