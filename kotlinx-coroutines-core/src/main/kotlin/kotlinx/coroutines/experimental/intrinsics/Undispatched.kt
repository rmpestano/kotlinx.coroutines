/*
 * Copyright 2016-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlinx.coroutines.experimental.intrinsics

import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.intrinsics.*
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Use this function to restart coroutine directly from inside of [suspendCoroutine].
 *
 * @suppress **This is unstable API and it is subject to change.**
 */
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
internal fun <R> (suspend () -> R).startCoroutineUndispatched(completion: Continuation<R>) {
    val value = try {
        startCoroutineUninterceptedOrReturn(completion)
    } catch (e: Throwable) {
        completion.resumeWithException(e)
        return
    }
    if (value !== COROUTINE_SUSPENDED)
        completion.resume(value as R)
}

/**
 * Use this function to restart coroutine directly from inside of [suspendCoroutine].
 *
 * @suppress **This is unstable API and it is subject to change.**
 */
@Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
internal fun <E, R> (suspend (E) -> R).startCoroutineUndispatched(element: E, completion: Continuation<R>) {
    val value = try {
        startCoroutineUninterceptedOrReturn(element, completion)
    } catch (e: Throwable) {
        completion.resumeWithException(e)
        return
    }
    if (value !== COROUTINE_SUSPENDED)
        completion.resume(value as R)
}
