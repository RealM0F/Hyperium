package com.chattriggers.ctjs.minecraft.wrappers.objects.threading

import java.util.*

class ThreadHandler {
    private val activeThreads: MutableList<WrappedThread>

    init {
        this.activeThreads = ArrayList()
    }

    fun addThread(thread: WrappedThread) =
        this.activeThreads.add(thread)

    fun removeThread(thread: WrappedThread) =
        activeThreads.removeIf { thread1 -> thread1 === thread }

    fun stopThreads() {
        activeThreads.forEach { it.interrupt() }
        activeThreads.clear()
    }
}
