package de.stetro.tgtggrabber.api

import retrofit2.Retrofit

class IFTTTRepository(retrofit: Retrofit) {
    private val iftttService = retrofit.create(IFTTTService::class.java)

    fun sendNotification(iftttEvent: String, iftttKey: String) {
        println("send push notification")
        iftttService.triggerPush(iftttEvent, iftttKey).subscribe()
    }
}
