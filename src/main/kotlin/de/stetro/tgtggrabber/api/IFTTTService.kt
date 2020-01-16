package de.stetro.tgtggrabber.api

import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface IFTTTService {
    @POST(" trigger/{event}/with/key/{iftttkey}")
    fun triggerPush(
        @Path("event") iftttEvent: String,
        @Path("iftttkey") iftttKey: String
    ): Observable<Response<Unit>>
}
