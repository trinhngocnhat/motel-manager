-- owner--
INSERT INTO Users
VALUES
(
    'O001',
    'owner',
    '123',
    N'Chủ nhà',
    '0900000000',
    'owner@motel.com',
    'OWNER',
    GETDATE()
);

--manager--
INSERT INTO Users
VALUES
(
    'M001',
    'manager1',
    '123',
    N'Nguyễn Văn Quản Lý A',
    '0900000001',
    'manager1@motel.com',
    'MANAGER',
    GETDATE()
),
(
    'M002',
    'manager2',
    '123',
    N'Trần Văn Quản Lý B',
    '0900000002',
    'manager2@motel.com',
    'MANAGER',
    GETDATE()
);

--200 tenants--
DECLARE @i INT = 1;

WHILE @i <= 200
BEGIN

    INSERT INTO Users
    (
        UserID,
        Username,
        Password,
        FullName,
        Phone,
        Email,
        Role
    )
    VALUES
    (
        CONCAT('T',FORMAT(@i,'000')),
        CONCAT('tenant',@i),
        '123',
        N'Người thuê ' + CAST(@i AS NVARCHAR),
        CONCAT('09',FORMAT(@i,'00000000')),
        CONCAT('tenant',@i,'@gmail.com'),
        'TENANT'
    );

    SET @i += 1;
END;

--room--
DECLARE @i INT = 1;

WHILE @i <= 100
BEGIN

    INSERT INTO Rooms
    (
        RoomID,
        TypeRooms,
        Price,
        Status,
        Floor,
        Note,
        ManagerID
    )
    VALUES
    (
        CONCAT('A',FORMAT(@i,'000')),

        CASE
            WHEN @i % 5 = 0
            THEN N'Phòng tốt'
            ELSE N'Phòng thường'
        END,

        CASE
            WHEN @i % 5 = 0
            THEN 5000000
            ELSE 3000000
        END,

        'OCCUPIED',

        ((@i-1)/20)+1,

        N'Khu A',

        'M001'
    );

    SET @i += 1;
END;
GO

DECLARE @i INT = 1;

WHILE @i <= 100
BEGIN

    INSERT INTO Rooms
    (
        RoomID,
        TypeRooms,
        Price,
        Status,
        Floor,
        Note,
        ManagerID
    )
    VALUES
    (
        CONCAT('B',FORMAT(@i,'000')),

        CASE
            WHEN @i % 5 = 0
            THEN N'Phòng tốt'
            ELSE N'Phòng thường'
        END,

        CASE
            WHEN @i % 5 = 0
            THEN 5000000
            ELSE 3000000
        END,

        'OCCUPIED',

        ((@i-1)/20)+1,

        N'Khu B',

        'M002'
    );

    SET @i += 1;
END;
GO

-- Rental Contracts--
DECLARE @i INT = 1;

DECLARE
    @StartDate DATE,
    @Months INT;

WHILE @i <= 200
BEGIN

    SET @Months =
        FLOOR(RAND(CHECKSUM(NEWID())) * 10) + 3;

    SET @StartDate =
        DATEADD
        (
            DAY,
            -FLOOR(RAND(CHECKSUM(NEWID()))*365),
            GETDATE()
        );

    INSERT INTO RentalContracts
    (
        ContractID,
        UserID,
        RoomID,
        StartDate,
        EndDate,
        MonthlyRent,
        Deposit,
        Status
    )
    VALUES
    (
        CONCAT('C',FORMAT(@i,'000')),

        CONCAT('T',FORMAT(@i,'000')),

        CASE
            WHEN @i <= 100
                THEN CONCAT('A',FORMAT(@i,'000'))
            ELSE
                CONCAT('B',FORMAT(@i-100,'000'))
        END,

        @StartDate,

        DATEADD(MONTH,@Months,@StartDate),

        CASE
            WHEN @i % 5 = 0
                THEN 5000000
            ELSE 3000000
        END,

        CASE
            WHEN @i % 5 = 0
                THEN 10000000
            ELSE 6000000
        END,

        CASE
            WHEN DATEADD(MONTH,@Months,@StartDate) > GETDATE()
                THEN 'ACTIVE'
            ELSE 'EXPIRED'
        END
    );

    SET @i += 1;
END;

--Maintenance Records--
DECLARE @i INT = 1;

WHILE @i <= 400
BEGIN

    INSERT INTO MaintenanceHistory
    (
        MaintenanceID,
        RoomID,
        Description,
        Cost,
        RepairDate,
        CreatedBy
    )
    VALUES
    (
        CONCAT('MT',FORMAT(@i,'000')),

        CASE
            WHEN @i <= 200
                THEN CONCAT('A',FORMAT(((@i-1)%100)+1,'000'))
            ELSE
                CONCAT('B',FORMAT(((@i-1)%100)+1,'000'))
        END,

        CASE (@i % 5)
            WHEN 0 THEN N'Sửa máy lạnh'
            WHEN 1 THEN N'Thay khóa cửa'
            WHEN 2 THEN N'Sửa đường nước'
            WHEN 3 THEN N'Thay bóng đèn'
            ELSE N'Sơn lại phòng'
        END,

        FLOOR(RAND(CHECKSUM(NEWID()))*3000000)+200000,

        DATEADD
        (
            DAY,
            -FLOOR(RAND(CHECKSUM(NEWID()))*365),
            GETDATE()
        ),

        CASE
            WHEN @i % 2 = 0
                THEN 'M001'
            ELSE 'M002'
        END
    );

    SET @i += 1;
END;