const express = require('express');
const router = express.Router();
const db = require('../database/db');

router.post('/login', async (req, res) => {
  const { username, password } = req.body;

  if (!username || !password) {
    return res.status(400).json({ error: 'Username and password are required' });
  }

  try {
    const result = await db.query(
      'SELECT UserID, Username, Role FROM Users WHERE Username = @username AND Password = @password',
      [
        { name: 'username', value: username },
        { name: 'password', value: password },
      ]
    );

    const user = result.recordset[0];

    if (!user) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    return res.json({ 
      user: {
        id: user.UserID,
        username: user.Username,
        role: user.Role
      },
      token: `fake-token-${user.UserID}` 
    });
  } catch (error) {
    console.error(error);
    return res.status(500).json({ error: 'Login failed' });
  }
});

module.exports = router;
