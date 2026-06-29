const express = require('express');
require('dotenv').config();
const router = express.Router();
const db = require('../database/db');

router.get('/', async (req, res) => {
  try {
    const managerId = 'M002';

    // 2. Truy vấn có điều kiện WHERE, truyền tham số an toàn
    const result = await db.query(
      'SELECT * FROM vw_AllRoomsAndContracts WHERE ManagerID = @ManagerID',
      [{ name: 'ManagerID', value: managerId }]
    );

    // 2. Trả về dữ liệu thành công cho Frontend/Postman
    return res.status(200).json({
      success: true,
      message: 'Lấy dữ liệu từ VIEW thành công',
      totalRecords: result.recordset.length, // Tổng số bản ghi lấy được
      data: result.recordset                 // Mảng chứa toàn bộ dữ liệu
    });

  } catch (error) {
    // 3. Xử lý lỗi nếu có (ví dụ: VIEW chưa được tạo, mất kết nối DB)
    console.error('❌ Error fetching view data:', error.message);
    
    return res.status(500).json({
      success: false,
      message: 'Lỗi server khi truy vấn database',
      errorDetail: error.message // Giúp bạn dễ debug trên Postman
    });
  }
});


module.exports = router;