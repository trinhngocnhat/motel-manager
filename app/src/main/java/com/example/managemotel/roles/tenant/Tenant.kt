package com.example.managemotel.roles.tenant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.components.*
import com.example.managemotel.models.NotificationItem
import com.example.managemotel.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun TenantScreen(navController: NavController) {

    // --- Dữ liệu thanh toán ---
    val roomNumber   = "502"
    val roomFee      = 2_500_000
    val electricKwh  = 120
    val electricRate = 3_500
    val waterM3      = 8
    val waterRate    = 20_000
    val serviceFee   = 100_000
    val electricFee  = electricKwh * electricRate
    val waterFee     = waterM3 * waterRate
    val totalFee     = roomFee + electricFee + waterFee + serviceFee
    val debtAmount   = 500_000
    val isPaid       = true

    // NEW - Tính số ngày còn lại hoặc quá hạn dựa trên thời gian thực
    val dueDateStr   = "20/06/2026"
    val formatter    = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val dueDate      = LocalDate.parse(dueDateStr, formatter)
    val today        = LocalDate.now()
    val dayDiff      = ChronoUnit.DAYS.between(today, dueDate)
    val dueDateLabel = when {
        dayDiff > 0  -> "Còn $dayDiff ngày"
        dayDiff < 0  -> "Quá hạn ${-dayDiff} ngày"
        else         -> "Hôm nay là hạn cuối"
    }
    val dueDateColor = when {
        dayDiff >= 5 -> Color(0xFFFF6F00)
        dayDiff >= 0 -> Color(0xFFE65100)
        else         -> Color(0xFFC62828)
    }

    // NEW - Trạng thái card thanh toán
    val statusValue = if (isPaid) "Đã thu" else "Công nợ"
    val statusIcon  = if (isPaid) Icons.Default.CheckCircle else Icons.Default.Warning
    val statusColor = if (isPaid) Color(0xFF2E7D32) else Color(0xFFC62828)
    val statusSub   = if (isPaid) null else "%,dđ".format(debtAmount).replace(",", ".")

    // NEW - Trạng thái mở rộng Chi tiết hóa đơn
    var billExpanded by remember { mutableStateOf(false) }

    // NEW - Dữ liệu thông báo mẫu
    val notifications = listOf(
        NotificationItem(1, "Hóa đơn tháng 06", "Hóa đơn tháng 06 đã được phát hành.", "2 giờ trước", false),
        NotificationItem(2, "Nhắc hạn thanh toán", "Hạn thanh toán là 20/06/2025.", "5 giờ trước", false),
        NotificationItem(3, "Yêu cầu đã tiếp nhận", "Yêu cầu sửa chữa internet đã được tiếp nhận.", "Hôm qua", true),
        NotificationItem(4, "Cập nhật nội quy", "Nội quy nhà trọ cập nhật từ 01/07/2025.", "2 ngày trước", true),
        NotificationItem(5, "Lịch bảo trì", "Thông báo lịch bảo trì điện nước.", "3 ngày trước", true),
    )

    Scaffold(
        topBar = {
            CommonHeader(
                roleName = "NGƯỜI THUÊ - PHÒNG $roomNumber",
                navController = navController
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = AppDimensions.PaddingLarge),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.SpacingLarge)
        ) {
            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall)) }

            // Hàng 1: Tổng tiền + Trạng thái
            item {
                SectionTitle(title = "Thanh toán tháng này")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                ) {
                    // Card Tổng tiền — chỉ hiển thị, không có popup
                    MetricCard(
                        label = "Tổng tiền",
                        value = "%,dđ".format(totalFee).replace(",", "."),
                        icon = Icons.Default.ReceiptLong,
                        color = Color(0xFF007ACC),
                        modifier = Modifier.weight(1f).height(120.dp)
                    )
                    // Card Trạng thái — động theo isPaid
                    MetricCard(
                        label = "Trạng thái",
                        value = statusValue,
                        subValue = statusSub,
                        icon = statusIcon,
                        color = statusColor,
                        modifier = Modifier.weight(1f).height(120.dp)
                    )
                }
            }

            // NEW - Hàng 2: Hạn thanh toán + Yêu cầu
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingMedium)
                ) {
                    // Card Hạn thanh toán với số ngày thực tế
                    MetricCard(
                        label = "Hạn thanh toán",
                        value = dueDateStr,
                        subValue = dueDateLabel,
                        icon = Icons.Default.CalendarMonth,
                        color = dueDateColor,
                        modifier = Modifier.weight(1f).height(120.dp)
                    )
                    // NEW - Card Yêu cầu — navigate sang RequestScreen
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(120.dp)
                            .clickable { navController.navigate("request") },
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF6A1B9A).copy(alpha = 0.15f)
                        ),
                        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
                        border = BorderStroke(
                            1.dp, Color(0xFF6A1B9A).copy(alpha = 0.35f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(AppDimensions.PaddingMedium),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null,
                                tint = Color(0xFF6A1B9A),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))
                            Text(
                                text = "Gửi yêu cầu",
                                fontSize = AppTypography.SizeLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF6A1B9A)
                            )
                            Text(
                                text = "Yêu cầu",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }

            // NEW - Section Chi tiết hóa đơn (ExpandableCard, thay cho Chỉ số dịch vụ)
            item {
                SectionTitle(title = "Chi tiết hóa đơn")
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    ),
                    shape = RoundedCornerShape(AppDimensions.RadiusLarge)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Header card — nhấn để mở/đóng
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { billExpanded = !billExpanded }
                                .padding(AppDimensions.PaddingMedium),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tổng: %,dđ".format(totalFee).replace(",", "."),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF007ACC)
                            )
                            Icon(
                                imageVector = if (billExpanded)
                                    Icons.Default.KeyboardArrowUp
                                else
                                    Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // NEW - Nội dung mở rộng với animation
                        AnimatedVisibility(
                            visible = billExpanded,
                            enter = expandVertically(),
                            exit = shrinkVertically()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = AppDimensions.PaddingMedium,
                                        end = AppDimensions.PaddingMedium,
                                        bottom = AppDimensions.PaddingMedium
                                    ),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                HorizontalDivider()
                                Spacer(modifier = Modifier.height(4.dp))

                                BillDetailRow(label = "Tiền phòng", value = "%,dđ".format(roomFee).replace(",", "."))

                                BillDetailRow(
                                    label = "Điện",
                                    value = "%,dđ".format(electricFee).replace(",", "."),
                                    subLabel = "$electricKwh kWh × %,dđ".format(electricRate).replace(",", ".")
                                )

                                BillDetailRow(
                                    label = "Nước",
                                    value = "%,dđ".format(waterFee).replace(",", "."),
                                    subLabel = "$waterM3 m³ × %,dđ".format(waterRate).replace(",", ".")
                                )

                                BillDetailRow(label = "Dịch vụ", value = "%,dđ".format(serviceFee).replace(",", "."))

                                HorizontalDivider()

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Tổng cộng", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF007ACC))
                                    Text("%,dđ".format(totalFee).replace(",", "."), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color(0xFF007ACC))
                                }
                            }
                        }
                    }
                }
            }

            // NEW - Section Thông báo
            item {
                SectionTitle(
                    title = "Thông báo",
                    onActionClick = { navController.navigate("notification") }
                )
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    notifications.take(3).forEach { notif ->
                        NotificationCard(item = notif)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(AppDimensions.SpacingLarge)) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TenantScreenPreview() {
    val navController = rememberNavController()
    TenantScreen(navController = navController)
}

// NEW - Composable dòng chi tiết hóa đơn trong ExpandableCard
@Composable
private fun BillDetailRow(
    label: String,
    value: String,
    subLabel: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(text = label, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
            if (subLabel != null) {
                Text(text = subLabel, fontSize = 11.sp, color = Color.Gray)
            }
        }
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
    }
}
