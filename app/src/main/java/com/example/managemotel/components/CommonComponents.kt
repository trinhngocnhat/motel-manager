package com.example.managemotel.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.managemotel.models.NotificationItem
import com.example.managemotel.ui.theme.AppPrimaryBlue
import com.example.managemotel.ui.theme.AppDimensions
import com.example.managemotel.ui.theme.AppTypography
import com.example.managemotel.ui.theme.AppVisualizeBg

@Composable
fun CommonHeader(roleName: String, navController: NavController, showBackButton: Boolean = false) {
    Surface(
        modifier = Modifier.fillMaxWidth(), 
        color = AppPrimaryBlue, 
        tonalElevation = 8.dp,
        shape = RoundedCornerShape(bottomStart = AppDimensions.RadiusLarge, bottomEnd = AppDimensions.RadiusLarge)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppDimensions.PaddingLarge, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showBackButton) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            } else {
                Icon(
                    imageVector = Icons.Default.AccountCircle, 
                    contentDescription = "Logo", 
                    tint = Color.White, 
                    modifier = Modifier.size(40.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(AppDimensions.PaddingMedium))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "M-MOTEL", 
                    color = Color.White.copy(alpha = 0.7f), 
                    fontSize = AppTypography.SizeSmall,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = roleName, 
                    color = Color.White, 
                    fontSize = AppTypography.SizeHeader, 
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(onClick = {
                navController.navigate("login") { popUpTo(0) { inclusive = true } }
            }) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Đăng xuất", tint = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CommonHeaderPreview() {
    val navController = rememberNavController()
    CommonHeader(roleName = "PREVIEW MODE", navController = navController)
}

@Composable
fun SectionTitle(title: String, onActionClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = AppTypography.SizeLarge,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
        if (onActionClick != null) {
            TextButton(onClick = onActionClick, contentPadding = PaddingValues(0.dp)) {
                Text("Xem tất cả", fontSize = AppTypography.SizeNormal)
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}

@Composable
fun CommonLabelsRow(labels: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(AppDimensions.SpacingSmall)
    ) {
        labels.forEach { label ->
            FilterChip(
                selected = false,
                onClick = {},
                label = { Text(label) },
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = Color.White,
                    labelColor = Color.Gray
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = Color.LightGray, 
                    borderWidth = 1.dp,
                    selected = false,
                    enabled = true
                )
            )
        }
    }
}

@Composable
fun MetricCard(
    label: String,
    value: String,
    subValue: String? = null,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier,
        onClick = { onClick?.invoke() },
        enabled = onClick != null,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
        border = BorderStroke(1.dp, color.copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppDimensions.PaddingMedium),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(AppDimensions.SpacingSmall))
            Text(
                text = value,
                fontSize = AppTypography.SizeLarge,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            if (subValue != null) {
                Text(
                    text = subValue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = color
                )
            }
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun VisualizeBox(title: String, height: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        colors = CardDefaults.cardColors(containerColor = AppVisualizeBg),
        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.AccountCircle, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title, 
                    color = Color.Gray, 
                    fontSize = AppTypography.SizeNormal,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun MotelTable(headers: List<String>, rows: List<List<String>>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(AppDimensions.RadiusLarge),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            // Header
            Row(modifier = Modifier.background(Color.LightGray.copy(alpha = 0.2f)).padding(12.dp)) {
                headers.forEach { header ->
                    Text(
                        text = header,
                        modifier = Modifier.width(100.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = AppTypography.SizeNormal
                    )
                }
            }
            // Rows
            rows.forEach { rowData ->
                Row(modifier = Modifier.padding(12.dp)) {
                    rowData.forEach { cell ->
                        Text(
                            text = cell,
                            modifier = Modifier.width(100.dp),
                            fontSize = AppTypography.SizeNormal
                        )
                    }
                }
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.2f))
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = if (isTotal) Color.Black else Color.Gray, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal)
        Text(text = value, color = if (isTotal) AppPrimaryBlue else Color.DarkGray, fontWeight = if (isTotal) FontWeight.ExtraBold else FontWeight.Medium)
    }
}

@Composable
fun NotificationCard(item: NotificationItem, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (item.isRead)
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            else
                Color(0xFF007ACC).copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(AppDimensions.RadiusLarge)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppDimensions.PaddingMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Chấm màu phân biệt đã đọc / chưa đọc
            Box(
                modifier = Modifier
                    .size(8.dp)
            ) {
                if (!item.isRead) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFF007ACC),
                                shape = RoundedCornerShape(4.dp)
                            )
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontSize = 13.sp,
                    fontWeight = if (item.isRead) FontWeight.Normal else FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = item.content,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = item.timestamp,
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}
