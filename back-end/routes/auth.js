const express = require('express');
require('dotenv').config();
const router = express.Router();
const db = require('../database/db');

router.post('/login', async (req, res) => {
  const { Email, Password } = req.body;

  if (!Email || !Password) {
    return res.status(400).json({ error: 'Email and Password are required' });
  }

  try {
    // 1. Fixed SQL SELECT to match exact database casing: UserID, Role
    const result = await db.query(
      'SELECT UserID, Username, Email, Role FROM dbo.Users WHERE (Email = @Email OR Username = @Email) AND Password = @Password',
      [
        { name: 'Email', value: Email },
        { name: 'Password', value: Password },
      ]
    );

    const user = result.recordset[0];

    if (!user) {
      return res.status(401).json({ error: 'Invalid credentials' });
    }

    return res.json({ 
      user: {
        id: user.UserID,       // 2. Fixed casing
        name: user.Username,
        Email: user.Email,
        role: user.Role        // 3. Fixed casing
      },
      token: `fake-token-${user.UserID}` // 4. Fixed casing
    });
  } catch (error) {
    console.error('Login error:', error);
    return res.status(500).json({ error: 'Login failed' });
  }
});

module.exports = router;