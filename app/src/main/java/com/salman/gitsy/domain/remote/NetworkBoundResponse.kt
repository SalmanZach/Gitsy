package com.salman.gitsy.domain.remote

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.*
import retrofit2.Response


abstract class NetworkBoundResponse<RESULT, REQUEST> {

    fun asFlow() = flow<Envelope<RESULT>> {

        // Emit Loading State
        emit(Envelope.loading())

        // Emit Database content first
        fetchFromLocal().first()?.let { result ->
            emit(Envelope.success(result))
        }

        // Fetch latest posts from remote
        val apiResponse = fetchFromRemote()

        // Parse body
        val remotePosts = apiResponse.body()

        // Check for response validation
        if (apiResponse.isSuccessful && remotePosts != null) {
            // Save posts into the persistence storage
            saveRemoteData(remotePosts)
        } else {
            // Something went wrong! Emit Error state.
            emit(Envelope.error(apiResponse.errorBody()?.string() ?: "Something went wrong!"))

        }
        // Retrieve posts from persistence storage and emit
        emitAll(
            fetchFromLocal().map {
                Envelope.success(it)
            }
        )
    }.catch { e ->
        // Exception occurred! Emit error pe.error(error))
        e.printStackTrace()
    }

    /**
     * Saves retrieved from remote into the persistence storage.
     */
    @WorkerThread
    protected abstract suspend fun saveRemoteData(response: REQUEST)

    /**
     * Retrieves all data from persistence storage.
     */
    @MainThread
    protected abstract fun fetchFromLocal(): Flow<RESULT>

    /**
     * Fetches [Response] from the remote end point.
     */
    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<REQUEST>


}
