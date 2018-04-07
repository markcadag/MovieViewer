package com.markcadag.movieviewer.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable


/**
 * Created by markcadag on 4/7/18.
 */
class RxSchedulersOverrideRule : org.junit.rules.TestRule {

    private val mRxAndroidSchedulersHook = object : Function<Callable<Scheduler>, Scheduler> {
        @Throws(Exception::class)
        override fun apply(@NonNull schedulerCallable: Callable<Scheduler>): Scheduler {
            return scheduler
        }
    }

    private val mRxJavaImmediateScheduler = object : Function<Scheduler, Scheduler> {
        @Throws(Exception::class)
        override fun apply(@NonNull scheduler: Scheduler): Scheduler {
            return scheduler
        }
    }

    val scheduler: Scheduler
        get() = Schedulers.trampoline()

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(mRxAndroidSchedulersHook)

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(mRxJavaImmediateScheduler)
                RxJavaPlugins.setNewThreadSchedulerHandler(mRxJavaImmediateScheduler)

                base.evaluate()

                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }

}