const path = require('path');

// Explicitly point to the .env file in the parent directory (back-end)
require('dotenv').config({ path: path.join(__dirname, '..', '.env') });

const sql = require('mssql');

const config = {
    user: process.env.DB_USER,
    password: process.env.DB_PASSWORD,
    server: process.env.DB_SERVER,
    database: process.env.DB_NAME,
    port: parseInt(process.env.DB_PORT, 10),
    options: {
        trustServerCertificate: process.env.DB_TRUST_SERVER_CERT === 'true',
        encrypt: false 
    }
};

console.log("Attempting to connect with these settings:");
console.log(`Server: ${config.server}`);
console.log(`Database: ${config.database}`);
console.log(`Port: ${config.port}`);
console.log("------------------------------------------------");

sql.connect(config)
    .then(pool => {
        console.log("✅ SUCCESS! Your .env file is 100% correct and connected.");
        console.log("Connected to SQL Server successfully.");
        return pool.close();
    })
    .catch(err => {
        console.error("❌ FAILED! Check the error below:");
        console.error(err.message);
        
        if (err.message.includes("Login failed for user 'sa'")) {
            console.log("👉 Hint: Check your DB_USER and DB_PASSWORD in the .env file.");
        } else if (err.message.includes("getaddrinfo ENOTFOUND") || err.message.includes("Failed to connect")) {
            console.log("👉 Hint: Check your DB_SERVER name or ensure TCP/IP is enabled in SQL Server Configuration Manager.");
        } else if (err.message.includes("Self-signed certificate")) {
            console.log("👉 Hint: Ensure DB_TRUST_SERVER_CERT=true in your .env file.");
        }
    });