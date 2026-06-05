const express = require('express');
const router = express.Router();
const db = require('../database/db');

router.post('/login', async (req, res) => {
  const { email, password } = req.body;

  if (!email || !password) {
    return res.status(400).json({ error: 'Email and password are required' });
  }

  try {
    const result = await db.query(
      'SELECT id, name, email, role FROM dbo.users WHERE email = @email AND password = @password',
      [
        { name: 'email', value: email },
        { name: 'password', value: password },
      ]
    );

    const user = result.recordset[0];

    if (!user) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    return res.json({ user, token: `fake-token-${user.id}` });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: 'Login failed' });
  }
});

router.post('/register', async (req, res) => {
  const { name, email, password, role } = req.body;

  if (!name || !email || !password || !role) {
    return res.status(400).json({ error: 'Name, email, password, and role are required' });
  }

  try {
    const result = await db.query(
      'INSERT INTO dbo.users (name, email, password, role) OUTPUT INSERTED.id VALUES (@name, @email, @password, @role)',
      [
        { name: 'name', value: name },
        { name: 'email', value: email },
        { name: 'password', value: password },
        { name: 'role', value: role },
      ]
    );

    const insertedUser = result.recordset[0];
    return res.status(201).json({ id: insertedUser.id, name, email, role });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: 'Registration failed' });
  }
});

module.exports = router;
