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

async function exportAllRoomsAndContracts() {
    let pool;
    try {
        console.log('🔌 Connecting to SQL Server...');
        pool = await sql.connect(config);
        console.log('✅ Connected successfully!\n');

        // Fetch all data from the vw_AllRoomsAndContracts view
        console.log('🏢 === ALL ROOMS AND CONTRACTS (vw_AllRoomsAndContracts) ===');
        
        // Truy vấn toàn bộ dữ liệu từ VIEW
        const result = await pool.request().query('SELECT * FROM vw_AllRoomsAndContracts');
        
        // Kiểm tra nếu không có dữ liệu
        if (result.recordset.length === 0) {
            console.log('⚠️ VIEW tồn tại nhưng hiện tại chưa có dữ liệu nào (có thể do chưa có hợp đồng nào).');
        } else {
            console.log(`📊 Tìm thấy ${result.recordset.length} bản ghi.\n`);
            // In dữ liệu dạng bảng ra terminal
            console.table(result.recordset);
        }

        console.log('\n🎉 Data export completed successfully!');

    } catch (err) {
        console.error('❌ Error exporting data:', err.message);
        
        // Gợi ý lỗi cụ thể nếu VIEW chưa được tạo trong SQL Server
        if (err.message.includes("Invalid object name 'vw_AllRoomsAndContracts'")) {
            console.log("👉 Hint: VIEW 'vw_AllRoomsAndContracts' chưa tồn tại trong Database. Hãy đảm bảo bạn đã chạy câu lệnh CREATE VIEW trong SQL Server.");
        }
    } finally {
        // Always close the connection
        if (pool) {
            await pool.close();
            console.log('🔌 Database connection closed.');
        }
    }
}

// Run the function
exportAllRoomsAndContracts();