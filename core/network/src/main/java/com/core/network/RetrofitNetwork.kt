package com.core.network

import com.core.network.models.AccountNetwork
import com.core.network.models.CategoryNetwork
import com.core.network.models.CreateTransactionRequestModel
import com.core.network.models.CreateTransactionResponseModel
import com.core.network.models.TransactionNetwork
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

internal interface NetworkApi {
    @GET("transactions/account/{accountId}/period")
    suspend fun getAccountTransactionsForPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String?,
        @Query("endDate") endDate: String?,
    ): List<TransactionNetwork>

    @GET("categories")
    suspend fun getCategories(): List<CategoryNetwork>

    @GET("accounts")
    suspend fun getAccounts(): List<AccountNetwork>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Path("id") id: Int,
        @Body account: AccountNetwork
    ): AccountNetwork

    @POST("transactions")
    suspend fun createTransaction(
        @Body transaction: CreateTransactionRequestModel
    ) : CreateTransactionResponseModel
}

/**
 * Фичи получают именно публичный интерфейс RemoteDataSource,
 * про имплементацию этого интерфейса они ничего не знают, так как он internal
 */
@Singleton
class RetrofitNetwork @Inject constructor() : RemoteDataSource {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.BEARER_TOKEN}")
            .build()
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .build()

    private val networkApi = Retrofit.Builder()
        .baseUrl("https://shmr-finance.ru/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: String?,
        endDate: String?
    ): List<TransactionNetwork> =
        networkApi.getAccountTransactionsForPeriod(
            accountId,
            startDate,
            endDate
        )

    override suspend fun getCategories(): List<CategoryNetwork> {
        return networkApi.getCategories()
    }

    override suspend fun getAccounts(): List<AccountNetwork> {
        return networkApi.getAccounts()
    }

    override suspend fun updateAccount(
        id: Int,
        account: AccountNetwork
    ): AccountNetwork {
        return networkApi.updateAccount(
            id = id,
            account = account
        )
    }

    override suspend fun createTransaction(transaction: CreateTransactionRequestModel): CreateTransactionResponseModel {
        return networkApi.createTransaction(transaction)
    }
}