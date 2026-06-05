IF DB_ID('motel_manager') IS NULL
  CREATE DATABASE motel_manager;
GO

USE motel_manager;
GO

IF OBJECT_ID('dbo.users', 'U') IS NULL
BEGIN
  CREATE TABLE dbo.users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
    role NVARCHAR(50) NOT NULL,
    createdAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
  );
END
GO

IF OBJECT_ID('dbo.owners', 'U') IS NULL
BEGIN
  CREATE TABLE dbo.owners (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL FOREIGN KEY REFERENCES dbo.users(id)
  );
END
GO

IF OBJECT_ID('dbo.managers', 'U') IS NULL
BEGIN
  CREATE TABLE dbo.managers (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL FOREIGN KEY REFERENCES dbo.users(id)
  );
END
GO

IF OBJECT_ID('dbo.tenants', 'U') IS NULL
BEGIN
  CREATE TABLE dbo.tenants (
    id INT IDENTITY(1,1) PRIMARY KEY,
    userId INT NOT NULL FOREIGN KEY REFERENCES dbo.users(id),
    roomNumber NVARCHAR(10) NULL
  );
END
GO
