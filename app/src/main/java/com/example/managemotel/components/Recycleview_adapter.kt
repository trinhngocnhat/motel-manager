package com.example.managemotel.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

/**
 * COMPONENT DÙNG CHUNG: Bạn có thể gọi hàm này ở bất kỳ màn hình nào.
 * Nó sử dụng Generic Type <T> nên có thể nhận bất kỳ loại dữ liệu nào (Khách thuê, Phòng, Hợp đồng...)
 * * @param pagingItems Dữ liệu phân trang từ ViewModel
 * @param modifier Chỉnh sửa giao diện (padding, fillMaxSize...)
 * @param itemContent Giao diện của từng dòng (bạn sẽ tự định nghĩa khi gọi hàm)
 */
@Composable
fun <T : Any> PaginatedLazyColumn(
    pagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Xử lý trạng thái tải lần đầu (Initial Load)
        if (pagingItems.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 1. Hiển thị danh sách dữ liệu
                items(count = pagingItems.itemCount) { index ->
                    val item = pagingItems[index]
                    if (item != null) {
                        itemContent(item) // Gọi hàm vẽ giao diện dòng được truyền vào
                    }
                }

                // 2. TỰ ĐỘNG xử lý trạng thái tải trang tiếp theo (Load More ở cuối danh sách)
                pagingItems.apply {
                    when {
                        // Trạng thái đang tải thêm
                        loadState.append is LoadState.Loading -> {
                            item { LoadingItem() }
                        }
                        // Trạng thái lỗi khi tải thêm
                        loadState.append is LoadState.Error -> {
                            val e = pagingItems.loadState.append as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage ?: "Lỗi kết nối",
                                    onRetryClick = { retry() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// ====================================================================
// CÁC COMPONENT GIAO DIỆN PHỤ TRỢ
// ====================================================================

@Composable
fun LoadingItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String, onRetryClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Button(onClick = onRetryClick) {
            Text("Thử lại")
        }
    }
}

// ====================================================================
// VÍ DỤ CÁCH BẠN SỬ DỤNG COMPONENT NÀY TRONG THỰC TẾ
// ====================================================================

/*
// Trong ViewModel của bạn, bạn thiết lập ngưỡng tải tiếp (threshold) ở đây:
val pager = Pager(
    config = PagingConfig(
        pageSize = 20,
        prefetchDistance = 3 // <-- ĐÂY CHÍNH LÀ THRESHOLD (Cách đáy 3 dòng thì tự gọi API tiếp)
    ),
    pagingSourceFactory = { MyPagingSource(apiService) }
).flow

// Ở màn hình Quản lý Phòng:
@Composable
fun QuanLyPhongScreen(viewModel: PhongViewModel) {
    val pagingRooms = viewModel.pager.collectAsLazyPagingItems()

    // CHỈ CẦN GỌI TÊN COMPONENT LÀ XONG!
    PaginatedLazyColumn(
        pagingItems = pagingRooms
    ) { phong ->
        // Giao diện của 1 phòng
        Card { Text("Phòng số: ${phong.tenPhong}") }
    }
}

// Ở màn hình Quản lý Khách thuê:
@Composable
fun QuanLyKhachScreen(viewModel: KhachViewModel) {
    val pagingKhach = viewModel.pager.collectAsLazyPagingItems()

    // Dùng lại hàm cũ nhưng truyền dữ liệu khác
    PaginatedLazyColumn(
        pagingItems = pagingKhach
    ) { khach ->
        // Giao diện của 1 khách thuê
        Row { Text("Tên khách: ${khach.ten}") }
    }
}
*/