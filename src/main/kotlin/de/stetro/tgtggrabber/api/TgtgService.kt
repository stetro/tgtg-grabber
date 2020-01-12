package de.stetro.tgtggrabber.api

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST


interface TgtgService {

    @POST("api/auth/v1/loginByEmail")
    @Headers("Accept-Language: DE")
    fun loginByEmail(@Body loginByEmailRequest: LoginByEmailRequest): Observable<Response<LoginResponse>>

    @POST("api/auth/v1/token/refresh")
    @Headers("Accept-Language: DE")
    fun refreshToken(@Body refreshToken: RefreshToken): Observable<Response<RefreshTokenResult>>

    @POST("api/item/v3/")
    @Headers("Accept-Language: DE")
    fun listFavorites(@Header("Authorization") accessToken: String, @Body listItemRequest: ListItemRequest): Observable<Response<ListItemResponse>>
}

data class ListItemResponse(val items: List<Item>)

data class Item(
    @SerializedName("display_description")
    val displayDescription: String,
    @SerializedName("display_name")
    val displayName: String,
    val distance: Double,
    val favorite: Boolean,
    @SerializedName("purchase_end")
    val purchaseEnd: String,
    @SerializedName("items_available")
    val itemsAvailable: Int,
    @SerializedName("sold_out_at")
    val soldOutAt: String
)

data class RefreshToken(
    @SerializedName("refresh_token")
    val refreshToken: String
)

data class RefreshTokenResult(
    @SerializedName("access_token")
    val accessToken: String
)

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("startup_data")
    val startupData: StartupResponse,
    @SerializedName("user_count")
    val userCount: Int
)

data class StartupResponse(val user: UserData)

data class UserData(
//    @SerializedName("country_code")
//    val countryCode: String,
//    val email: String,
//    val name: String,
//    @SerializedName("newsletter_opt_in")
//    val newsletterOptIn: Boolean,
//    @SerializedName("phone_country_code")
//    val phoneCountryCode: String,
//    @SerializedName("phone_number")
//    val phoneNumber: String,
//    @SerializedName("push_notification_opt_in")
//    val pushNotificationOptIn: Boolean,
//    val role: String,
    @SerializedName("user_id")
    val userId: String
)

data class LoginByEmailRequest(
    val email: String,
    val password: String,
    @SerializedName("device_type")
    val deviceType: DeviceType = DeviceType.ANDROID
)

enum class DeviceType {
    UNKNOWN, IOS, ANDROID
}

data class ListItemRequest(

    @SerializedName("hidden_only")
    val hiddenOnly: Boolean = false,

    @SerializedName("favorites_only")
    val favoritesOnly: Boolean = false,

    @SerializedName("discover")
    val discover: Boolean = false,

    @SerializedName("page")
    val page: Int = 1,

    @SerializedName("page_size")
    val pageSize: Int = 10,

    @SerializedName("radius")
    val radius: Double = 2.0,

    @SerializedName("origin")
    val origin: LatLngInfo = LatLngInfo(51.200974, 6.838755),

    @SerializedName("user_id")
    val userId: String
)

data class LatLngInfo(
    val latitude: Double,
    val longitude: Double
)

