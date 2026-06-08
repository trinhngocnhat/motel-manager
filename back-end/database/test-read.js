require('dotenv').config({ path: require('path').join(__dirname, '..', '.env') });
const sql = require('mssql');

// Database configuration from .env
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

async function exportDataToTerminal() {
    let pool;
    try {
        console.log('🔌 Connecting to SQL Server...');
        pool = await sql.connect(config);
        console.log('✅ Connected successfully!\n');

        // 1. Fetch Users
        console.log('👥 === USERS ===');
        const usersResult = await pool.request().query('SELECT UserID, Username, Role FROM Users');
        console.table(usersResult.recordset);

        // 2. Fetch Tenants
        console.log('🏠 === TENANTS ===');
        const tenantsResult = await pool.request().query('SELECT TenantID, FullName, Phone, Email FROM Tenants');
        console.table(tenantsResult.recordset);

        // 3. Fetch Managers
        console.log('💼 === MANAGERS ===');
        const managersResult = await pool.request().query('SELECT ManagerID, FullName, Phone, Email FROM Managers');
        console.table(managersResult.recordset);

        // 4. Fetch Owner
        console.log('👑 === OWNER ===');
        const ownerResult = await pool.request().query('SELECT OwnerID, FullName, Phone, Email FROM Owners');
        console.table(ownerResult.recordset);

        // 5. Fetch Rooms and their Types
        console.log('🚪 === ROOMS ===');
        const roomsResult = await pool.request().query(`
            SELECT 
                r.RoomID, 
                rt.TypeName AS RoomType, 
                rt.Price AS MonthlyPrice, 
                r.Status 
            FROM Rooms r
            JOIN RoomTypes rt ON r.TypeID = rt.TypeID
        `);
        console.table(roomsResult.recordset);

        // 6. Fetch Active Rental Contracts
        console.log('📜 === RENTAL CONTRACTS ===');
        const contractsResult = await pool.request().query(`
            SELECT 
                c.ContractID, 
                t.FullName AS TenantName, 
                r.RoomID, 
                c.StartDate, 
                c.EndDate, 
                c.Status 
            FROM RentalContracts c
            JOIN Tenants t ON c.TenantID = t.TenantID
            JOIN Rooms r ON c.RoomID = r.RoomID
        `);
        console.table(contractsResult.recordset);

        console.log('\n🎉 Data export completed successfully!');

    } catch (err) {
        console.error('❌ Error exporting data:', err.message);
    } finally {
        // Always close the connection
        if (pool) {
            await pool.close();
            console.log('🔌 Database connection closed.');
        }
    }
}

// Run the function
exportDataToTerminal();