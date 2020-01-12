package de.stetro.tgtggrabber.api

import retrofit2.Retrofit

class IFTTTRepository(retrofit: Retrofit) {
    val iftttService = retrofit.create(IFTTTService::class.java)

    fun sendNotification(iftttkey: String) {
        println("send push notification")
        iftttService.triggerPush(iftttkey).subscribe()
    }
}
