package de.stetro.tgtggrabber

import de.stetro.tgtggrabber.api.IFTTTRepository
import de.stetro.tgtggrabber.api.TgtgRepository
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit

class Grabber : KoinComponent {

    private val tgtgRepository: TgtgRepository by inject()
    private val iftttRepository: IFTTTRepository by inject()

    private var hasBeenAvailable = false

    fun run(username: String, password: String, iftttEvent: String, iftttKey: String) {
        println("start")
        Observable.interval(0, 5, TimeUnit.MINUTES)
            .map { tgtgRepository.getFavorites(username, password) }
            .blockingSubscribe { favorites ->
                favorites.subscribe({ listItemResponse ->
                    if (listItemResponse.items.isEmpty()) {
                        return@subscribe
                    }
                    val item = listItemResponse.items[0]
                    if ((item.itemsAvailable > 0)) {
                        println("${item.displayName} ist verfügbar")
                        if (!hasBeenAvailable) {
                            hasBeenAvailable = true
                            iftttRepository.sendNotification(iftttEvent, iftttKey)
                        }
                    } else {
                        println("${item.displayName} ist ausverkauft")
                        hasBeenAvailable = false
                    }
                }, {
                    it.printStackTrace()
                })
            }
    }
}