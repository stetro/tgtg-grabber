package de.stetro.tgtggrabber.api

import io.reactivex.Observable
import retrofit2.Retrofit

class TgtgRepository(retrofit: Retrofit) {

    private val tgtgService = retrofit.create(TgtgService::class.java)

    private var refreshToken: String? = null
    private var accessToken: String? = null
    private var userData: UserData? = null

    private fun getAccessToken(username: String, password: String): Observable<String> {
        return when {
            accessToken != null -> {
                println("Get cached access token")
                Observable.just(accessToken)
            }
            refreshToken != null -> {
                println("Get cached refresh token and refresh")
                tgtgService.refreshToken(RefreshToken(refreshToken!!))
                    .map {
                        require(it.isSuccessful)
                        require(it.body() != null)
                        require(it.body()?.accessToken != null)
                        println("refreshed")
                        accessToken = it.body()?.accessToken
                        it.body()?.accessToken
                    }
            }
            else -> {
                println("authenticate with email")
                tgtgService.loginByEmail(LoginByEmailRequest(username, password))
                    .map {
                        require(it.isSuccessful)
                        require(it.body() != null)
                        require(it.body()?.accessToken != null)
                        require(it.body()?.refreshToken != null)
                        println(it.body()?.startupData?.user)
                        println("authenticatication successfull")
                        refreshToken = it.body()?.refreshToken
                        userData = it.body()?.startupData?.user
                        accessToken = it.body()?.accessToken
                        it.body()?.accessToken
                    }
            }
        }
    }

    fun getFavorites(username: String, password: String): Observable<ListItemResponse> {
        println("get favorites")
        return getAccessToken(username, password)
            .flatMap { accessToken ->
                println("request favorites with token $accessToken")
                tgtgService.listFavorites(
                    "Bearer $accessToken",
                    ListItemRequest(favoritesOnly = true, userId = userData!!.userId)
                )
            }
            .map {
                println(it.message())
                require(it.isSuccessful)
                require(it.body() != null)
                it.body()!!
            }
    }
}
