package proj.skb.controllers

import io.swagger.annotations.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import proj.skb.ProjSkbConstants
import proj.skb.ProjSkbConstants.STUB_GET_FAIL
import proj.skb.ProjSkbConstants.STUB_GET_NOT_FOUND
import proj.skb.ProjSkbConstants.STUB_GET_OK
import proj.skb.common.CacheObject
import proj.skb.common.ISkbCache
import proj.skb.config.CacheProperties
import proj.skb.logics.cacher.SkbCacheContext
import proj.skb.models.RestCacheResponse
import springfox.documentation.annotations.ApiIgnore
import java.time.Duration
import java.time.format.DateTimeParseException
import javax.servlet.http.HttpServletResponse

@RestController
@Api(tags = ["Cache REST controller"], value = "possible operations for requests")
class CacheRESTController @Autowired
constructor(
    private val cacheService: ISkbCache,
    private val properties: CacheProperties
) {

    val log = LoggerFactory.getLogger(this::class.java)!!

    @RequestMapping("/")
    @ApiIgnore
    fun home(httpResponse: HttpServletResponse) {
        httpResponse.sendRedirect("/swagger-ui.html")
    }



    @ApiOperation(
        value = "Add record to the cache and return its ID",
        notes = "",
        response = String::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = RestCacheResponse::class),
            ApiResponse(code = 405, message = "Invalid input", response = RestCacheResponse::class)
        ]
    )
    @PostMapping(value = ["/cache"])
    @ResponseBody
    fun postData(
        @ApiParam(
            value = "Delay for storing the object in cache. After the delay the object will be either deleted or sent " +
                    "to the queue. Must be positive and in the range of ${ProjSkbConstants.DEFAULT_MIN_DELAY} and " +
                    ProjSkbConstants.DEFAULT_MAX_DELAY,
            required = false,
            defaultValue = ProjSkbConstants.DEFAULT_DELAY,
            format = "PnDTnHnMn.nS",
            example = "PT20.345S"
        )
        @RequestParam(value = "delay")
        delay: String? = null,

        @ApiParam(
            value = "Queue name where this object will be sent to after delay",
            required = false,
            example = "someQueue"
        )
        @RequestParam(value = "queue")
        queueName: String? = null,

        @ApiParam(
            value = "Object to be sent to the cache",
            required = true,
            example = "{\"someData\": \"some value\"}"
        )
        @RequestBody
        data: Any
    ): ResponseEntity<RestCacheResponse> {
        return try {
            val cacheQuery = CacheObject(
                data = data,
                queueName = queueName ?: "",
                delay = Duration.parse(delay) ?: Duration.parse(properties.defatulDelay)
            )
            val res = cacheService.setValue(cacheQuery)
            log.debug("Successfully cached $res")
            ResponseEntity.ok(RestCacheResponse.of(res))
        } catch (e: DateTimeParseException) {
            log.error("Parse exception: {}", e)
            ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(RestCacheResponse.of(e, data, queueName, delay))
        } catch (e: Throwable) {
            log.error("Storing in cache error: {}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @ApiOperation(
        value = "Add record to the cache and return its ID",
        notes = "",
        response = RestCacheResponse::class
    )
    @ApiResponses(
        value = [
            ApiResponse(code = 200, message = "Ok", response = RestCacheResponse::class),
            ApiResponse(code = 204, message = "Data not found in database", response = RestCacheResponse::class),
            ApiResponse(code = 405, message = "Invalid input", response = RestCacheResponse::class)
        ]
    )
    @GetMapping(value = ["/cache"])
    @ResponseBody
    fun getData(
        @ApiParam(
            value = "ID of the object in cache. That ID was get from the POST request",
            required = true,
            example = "09a3233a-9663-45b5-9844-7fee297ed282"
        )
        @RequestParam(value = "id")
        key: String,

        @ApiParam(
            value = "Flag to request a stub data",
            required = false,
            allowableValues = "$STUB_GET_OK, $STUB_GET_NOT_FOUND, $STUB_GET_FAIL",
            example = "ok"
        )
        @RequestParam(value = "stub")
        stub: String? = null

    ): ResponseEntity<RestCacheResponse> {
        return try {
            val context = SkbCacheContext(id = key, stub = stub ?: "")
            val res = cacheService.getValue(context)
            log.debug("Successfully requested for $key with result: $res")
            ResponseEntity.ok(RestCacheResponse.of(res))
        } catch (e: Throwable) {
            log.error("Get cached value error: {}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

}
