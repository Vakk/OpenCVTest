package com.vakk.myapplication.ui.list.base

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

typealias OnError = (Throwable) -> kotlin.Unit

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private var handler = Handler(Looper.getMainLooper())
    private var backgroundExecutor =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    protected val tasks = mutableMapOf<Any, Future<*>>()

    protected val onError: OnError = {
        it.printStackTrace()
    }

    open fun runOnMain(action: () -> Unit) {
        handler.post(action)
    }

    open fun runOnBackground(onError: OnError, token: Any? = null, action: () -> Unit) {
        token?.let { tasks[it]?.cancel(true) }
        runActionOnExecutor(backgroundExecutor, onError, action).apply {
            token?.let { tasks[it] = this }
        }
    }

    protected fun runActionOnExecutor(
        executor: ExecutorService,
        onError: OnError,
        action: () -> Unit
    ): Future<*> {
        return executor.submit {
            val result = runCatching(action)
            result.exceptionOrNull()?.let { throwable ->
                when (throwable) {
                    is InterruptedException -> Thread.currentThread().interrupt()
                    else -> runOnMain { onError(throwable) }
                }
            }
        }
    }

    override fun onCleared() {
        for (task in tasks) {
            task.value.cancel(true)
        }
    }
}