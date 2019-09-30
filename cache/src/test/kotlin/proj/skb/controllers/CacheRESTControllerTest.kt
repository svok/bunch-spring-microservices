package proj.skb.controllers

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import proj.skb.models.RestCacheResponse
import proj.skb.models.RestCacheStatus
import java.net.URL
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class CacheRESTControllerTest {

    @Autowired
    lateinit var controller: CacheRESTController
    @LocalServerPort
    private val port: Int = 0
    @Autowired
    private lateinit var restTemplate: TestRestTemplate


    val ID = "09a3233a-9663-45b5-9844-7fee297ed282"

    @Test
    fun controllerPostTest() {
        assertThat(controller).isNotNull
        val result = controller.postData(
            delay = "PT10S",
            queueName = "test",
            data = "some ObjeCt"
        )
        assertThat(result.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(result.body).isEqualTo(
            RestCacheResponse(
                id = result.body?.id ?: "nonono",
                data = "some ObjeCt",
                queue = "test",
                delay = "PT10S",
                status = RestCacheStatus.OK,
                errorMessage = null,
                stub = null
            )
        )
    }

    @Test
    fun restPostTest() {

        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val response = restTemplate.exchange(
            URL("http://localhost:$port/cache?queue=test&delay=PT10S").toString(),
            HttpMethod.POST,
            HttpEntity("{\"1\":\"some ObjeCt\"}", headers),
            RestCacheResponse::class.java
        )

        val result = response.body
        assertThat(result).isEqualTo(
            RestCacheResponse(
                id = result?.id ?: "nonono",
                data = mapOf( "1" to "some ObjeCt"),
                queue = "test",
                delay = "PT10S",
                status = RestCacheStatus.OK,
                errorMessage = null,
                stub = null
            )
        )

    }

    @Test
    fun stubGetOkTest() {

        val ID = "edd9ac0c-d58f-4c04-8672-9e451fe3ec1e"
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val response = restTemplate.exchange(
            URL("http://localhost:$port/cache?id=$ID&stub=ok").toString(),
            HttpMethod.GET,
            HttpEntity<String>(headers),
            RestCacheResponse::class.java
        )

        val result = response.body
        assertThat(result).isEqualTo(
            RestCacheResponse(
                id = ID,
                data = "Stored value",
                queue = "stub-queue",
                delay = "PT2M10S",
                status = RestCacheStatus.OK,
                errorMessage = null,
                stub = "ok"
            )
        )

    }

    @Test
    fun stubGetNotFoundTest() {

        val ID = "edd9ac0c-d58f-4c04-8672-9e451fe3ec1e"
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val response = restTemplate.exchange(
            URL("http://localhost:$port/cache?id=$ID&stub=not-found").toString(),
            HttpMethod.GET,
            HttpEntity<String>(headers),
            RestCacheResponse::class.java
        )

        val result = response.body
        assertThat(result).isEqualTo(
            RestCacheResponse(
                id = ID,
                data = null,
                queue = "",
                delay = null,
                status = RestCacheStatus.NOT_FOUND,
                errorMessage = null,
                stub = "not-found"
            )
        )

    }

    @Test
    fun stubGetFailTest() {

        val ID = "edd9ac0c-d58f-4c04-8672-9e451fe3ec1e"
        val headers = HttpHeaders()
        headers.add("Content-Type", "application/json")
        val response = restTemplate.exchange(
            URL("http://localhost:$port/cache?id=$ID&stub=fail").toString(),
            HttpMethod.GET,
            HttpEntity<String>(headers),
            RestCacheResponse::class.java
        )

        val result = response.body
        assertThat(result).isEqualTo(
            RestCacheResponse(
                id = ID,
                data = null,
                queue = "",
                delay = null,
                status = RestCacheStatus.FAIL,
                errorMessage = "Generated by stub exception",
                stub = "fail"
            )
        )

    }
}