const express = require('express');
const dotenv = require('dotenv');

dotenv.config();

const db = require('./database/db');
const authRoutes = require('./routes/auth');
const ownerRoutes = require('./routes/owner');
const managerRoutes = require('./routes/manager');
const tenantRoutes = require('./routes/tenant');
const authMiddleware = require('./middleware/auth');

const app = express();
app.use(express.json());

app.get('/', (req, res) => {
  res.json({ status: 'ok', service: 'motel-manager backend' });
});

app.use('/api/auth', authRoutes);
app.use('/api/owners', authMiddleware, ownerRoutes);
app.use('/api/managers', authMiddleware, managerRoutes);
app.use('/api/tenants', authMiddleware, tenantRoutes);

app.use((req, res) => {
  res.status(404).json({ error: 'Not found' });
});

const port = process.env.PORT || 3000;

async function start() {
  try {
    await db.connect();
    console.log('Database connected');
    app.listen(port, () => {
      console.log(`Backend started on http://localhost:${port}`);
    });
  } catch (error) {
    console.error('Startup failed:', error);
    process.exit(1);
  }
}

start();
