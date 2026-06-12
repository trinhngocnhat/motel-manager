
const sql = require('mssql');

const config = {
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  server: process.env.DB_SERVER,
  database: process.env.DB_NAME,
  port: parseInt(process.env.DB_PORT, 10) || 1433,
  options: {
    encrypt: false,
    trustServerCertificate: process.env.DB_TRUST_SERVER_CERT === 'true',
    enableArithAbort: true,
  },
};

let pool;

async function connect() {
  if (pool) {
    return pool;
  }

  pool = await sql.connect(config);
  return pool;
}

async function query(queryText, params = []) {
  const connection = await connect();
  const request = connection.request();

  params.forEach((param) => {
    request.input(param.name, param.value);
  });

  return request.query(queryText);
}

module.exports = {
  connect,
  query,
};
