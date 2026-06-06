package com.example.managemotel.roles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.managemotel.components.CommonHeader
import com.example.managemotel.components.NotificationCard
import com.example.managemotel.models.NotificationItem
import com.example.managemotel.ui.theme.*

@Composable
fun NotificationScreen(navController: NavController) {

    // NEW - Dữ liệu thông báo mẫu (Có thể lấy từ ViewModel sau này)
    val notifications = listOf(
        NotificationItem(1, "Hóa đơn tháng 06", "Hóa đơn tháng 06 đã được phát hành.", "2 giờ trước", false),
        NotificationItem(2, "Nhắc hạn thanh toán", "Hạn thanh toán là 20/06/2025.", "5 giờ trước", false),
        NotificationItem(3, "Yêu cầu đã tiếp nhận", "Yêu cầu sửa chữa internet đã được tiếp nhận.", "Hôm qua", true),
        NotificationItem(4, "Cập nhật nội quy", "Nội quy nhà trọ cập nhật từ 01/07/2025.", "2 ngày trước", true),
        NotificationItem(5, "Lịch bảo trì", "Thông báo lịch bảo trì điện nước.", "3 ngày trước", true),
        NotificationItem(6, "Thanh toán thành công", "Bạn đã thanh toán hóa đơn tháng 05.", "1 tuần trước", true),
        NotificationItem(7, "Chào mừng", "Chào mừng bạn đến với M-MOTEL.", "2 tuần trước", true)
    )

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "THÔNG BÁO",
                navController = navController,
                showBackButton = true
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
        ) {
            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall)) }

            items(notifications) { notification ->
                NotificationCard(item = notification)
            }

            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge)) }
        }
    }
}
