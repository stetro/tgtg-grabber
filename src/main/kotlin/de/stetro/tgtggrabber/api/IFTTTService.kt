package de.stetro.tgtggrabber.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface IFTTTService {
    @POST(" trigger/rewe/with/key/{iftttkey}")
    fun triggerPush(@Path("iftttkey") iftttkey: String): Observable<Response<Unit>>
}
