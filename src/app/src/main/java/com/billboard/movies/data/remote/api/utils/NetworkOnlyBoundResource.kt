package com.billboard.movies.data.remote.api.utils

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * A generic class that can provide a resource backed
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkOnlyBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork() {
        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            when (response) {
                is ApiSuccessResponse -> {
                    appExecutors.diskIO().execute {
                        val requestType = processResponse(response)
                        val resultType = processResult(requestType)
                        appExecutors.mainThread().execute {
                            setValue(Resource.success(resultType))
                        }
                    }
                }
                is ApiEmptyResponse -> {
                    appExecutors.mainThread().execute {
                        setValue(Resource.success(null))
                    }
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    setValue(Resource.error(response.errorMessage,null))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @WorkerThread
    protected open fun processResponse(response: ApiSuccessResponse<RequestType>) = response.body

    @WorkerThread
    protected abstract fun processResult(item: RequestType?): ResultType?

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}