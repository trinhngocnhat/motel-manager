const express = require('express');
const router = express.Router();
const db = require('../database/db');

router.get('/', async (req, res) => {
  try {
    const result = await db.query(
      `SELECT t.id, u.name, u.email, u.role, t.roomNumber
       FROM dbo.tenants t
       JOIN dbo.users u ON u.id = t.userId`
    );
    res.json(result.recordset);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Could not fetch tenants' });
  }
});

router.post('/', async (req, res) => {
  const { userId, roomNumber } = req.body;

  if (!userId) {
    return res.status(400).json({ error: 'userId is required' });
  }

  try {
    const result = await db.query(
      'INSERT INTO dbo.tenants (userId, roomNumber) OUTPUT INSERTED.id VALUES (@userId, @roomNumber)',
      [
        { name: 'userId', value: userId },
        { name: 'roomNumber', value: roomNumber || null },
      ]
    );
    res.status(201).json({ id: result.recordset[0].id, userId, roomNumber });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Could not create tenant' });
  }
});

module.exports = router;
