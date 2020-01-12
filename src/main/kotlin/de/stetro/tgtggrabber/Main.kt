package de.stetro.tgtggrabber

import org.koin.core.context.startKoin

fun main(args: Array<String>) {
    startKoin {
        modules(grabberModule)
    }
    Grabber().run(username = args[0], password = args[1], iftttkey=args[2])
}
