package com.example.managemotel.network

import com.example.managemotel.models.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // --- AUTHENTICATION ---
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    // --- OWNER ENDPOINTS ---
    @GET("api/owner/rooms")
    suspend fun getAllRooms(): Response<List<MotelRoom>>

    @GET("api/owner/tenants")
    suspend fun getAllTenants(): Response<List<User>>

    @GET("api/owner/reports/revenue")
    suspend fun getRevenueReport(): Response<Map<String, Double>>

    // --- MANAGER ENDPOINTS ---
    @GET("api/manager/{managerId}/rooms")
    suspend fun getAssignedRooms(@Path("managerId") managerId: String): Response<List<MotelRoom>>

    @POST("api/manager/contracts")
    suspend fun createContract(@Body contract: RentalContract): Response<RentalContract>

    @POST("api/manager/maintenance")
    suspend fun recordMaintenance(@Body maintenance: MaintenanceHistory): Response<MaintenanceHistory>

    // --- TENANT ENDPOINTS ---
    @GET("api/tenant/{userId}/contract")
    suspend fun getMyContract(@Path("userId") userId: String): Response<RentalContract>

    @GET("api/tenant/{userId}/room")
    suspend fun getMyRoomInfo(@Path("userId") userId: String): Response<MotelRoom>

    @POST("api/tenant/maintenance-request")
    suspend fun submitMaintenanceRequest(@Body request: MaintenanceHistory): Response<MaintenanceHistory>

    // --- SHARED / GENERIC ---
    @PUT("api/rooms/{id}")
    suspend fun updateRoom(@Path("id") id: String, @Body room: MotelRoom): Response<MotelRoom>
}
