-- USERS

INSERT INTO Users
VALUES
(
    'U001',
    'owner1',
    '123456',
    N'Nguyen Van Owner',
    '0900000001',
    'owner@gmail.com',
    'OWNER',
    GETDATE()
);

INSERT INTO Users
VALUES
(
    'U002',
    'manager1',
    '123456',
    N'Tran Van Manager',
    '0900000002',
    'manager@gmail.com',
    'MANAGER',
    GETDATE()
);

INSERT INTO Users
VALUES
(
    'U003',
    'tenant1',
    '123456',
    N'Le Van Tenant',
    '0900000003',
    'tenant@gmail.com',
    'TENANT',
    GETDATE()
);

-- ROOM TYPES

INSERT INTO RoomTypes
VALUES
(
    'RT01',
    N'Standard',
    200,
    N'Standard Room'
);

INSERT INTO RoomTypes
VALUES
(
    'RT02',
    N'VIP',
    500,
    N'VIP Room'
);

-- ROOMS

INSERT INTO Rooms
VALUES
(
    'R001',
    'RT01',
    'AVAILABLE',
    1,
    N'Near entrance'
);

INSERT INTO Rooms
VALUES
(
    'R002',
    'RT02',
    'OCCUPIED',
    2,
    N'VIP Room'
);

-- CONTRACTS

INSERT INTO RentalContracts
VALUES
(
    'C001',
    'U003',
    'R002',
    '2026-06-01',
    '2026-12-01',
    500,
    1000,
    'ACTIVE'
);

-- MAINTENANCE

INSERT INTO MaintenanceHistory
VALUES
(
    'M001',
    'R002',
    N'Air Conditioner Repair',
    150,
    '2026-06-10',
    'U002'
);