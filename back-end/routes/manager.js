const express = require('express');
const router = express.Router();
const db = require('../database/db');

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

module.exports = router;
