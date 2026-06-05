const express = require('express');
const router = express.Router();
const db = require('../database/db');

router.get('/', async (req, res) => {
  try {
    const result = await db.query(
      `SELECT o.id, u.name, u.email, u.role
       FROM dbo.owners o
       JOIN dbo.users u ON u.id = o.userId`
    );
    res.json(result.recordset);
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Could not fetch owners' });
  }
});

router.post('/', async (req, res) => {
  const { userId } = req.body;

  if (!userId) {
    return res.status(400).json({ error: 'userId is required' });
  }

  try {
    const result = await db.query(
      'INSERT INTO dbo.owners (userId) OUTPUT INSERTED.id VALUES (@userId)',
      [{ name: 'userId', value: userId }]
    );
    res.status(201).json({ id: result.recordset[0].id, userId });
  } catch (error) {
    console.error(error);
    res.status(500).json({ error: 'Could not create owner' });
  }
});

module.exports = router;
