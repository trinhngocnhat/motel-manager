const express = require('express');
const router = express.Router();
const db = require('../database/db');
const auth = require('../middleware/auth');

router.get('/', async (req, res) => {
  try {
    const result = await db.query(
      `SELECT m.id, u.name, u.email, u.role
       FROM dbo.managers m
       JOIN dbo.users u ON u.id = m.userId`
    );
    res.json(result.recordset);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Could not fetch managers' });
  }
});


router.post('/', async (req, res) => {
  const { userId } = req.body;

  if (!userId) {
    return res.status(400).json({ error: 'userId is required' });
  }

  try {
    const result = await db.query(
      'INSERT INTO dbo.managers (userId) OUTPUT INSERTED.id VALUES (@userId)',
      [{ name: 'userId', value: userId }]
    );
    res.status(201).json({ id: result.recordset[0].id, userId });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Could not create manager' });
  }
});


// Secured endpoint: get rooms assigned to a manager by managerId
router.get('/:managerId/rooms', auth, async (req, res) => {
  try {
    const managerId = req.params.managerId;

    const result = await db.query(
      'SELECT * FROM vw_AllRoomsAndContracts WHERE ManagerID = @ManagerID',
      [{ name: 'ManagerID', value: managerId }]
    );

    return res.status(200).json({
      success: true,
      message: 'Lấy dữ liệu phòng quản lý thành công',
      totalRecords: result.recordset.length,
      data: result.recordset,
    });

  } catch (error) {
    console.error('❌ Error fetching manager rooms:', error.message);
    return res.status(500).json({
      success: false,
      message: 'Lỗi server khi truy vấn database',
      errorDetail: error.message,
    });
  }
});

module.exports = router;
