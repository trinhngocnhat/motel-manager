USE motel_manager;
GO

IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email = 'owner@example.com')
  INSERT INTO dbo.users (name, email, password, role)
  VALUES ('Owner One', 'owner@example.com', '123', 'owner');
ELSE
  UPDATE dbo.users SET password = '123' WHERE email = 'owner@example.com';
GO

IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email = 'manager@example.com')
  INSERT INTO dbo.users (name, email, password, role)
  VALUES ('Manager One', 'manager@example.com', '123', 'manager');
ELSE
  UPDATE dbo.users SET password = '123' WHERE email = 'manager@example.com';
GO

IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email = 'tenant@example.com')
  INSERT INTO dbo.users (name, email, password, role)
  VALUES ('Tenant One', 'tenant@example.com', '123', 'tenant');
ELSE
  UPDATE dbo.users SET password = '123' WHERE email = 'tenant@example.com';
GO

IF NOT EXISTS(SELECT 1 FROM dbo.owners)
  INSERT INTO dbo.owners (userId)
  SELECT id FROM dbo.users WHERE email = 'owner@example.com';
GO

IF NOT EXISTS(SELECT 1 FROM dbo.managers)
  INSERT INTO dbo.managers (userId)
  SELECT id FROM dbo.users WHERE email = 'manager@example.com';
GO

IF NOT EXISTS(SELECT 1 FROM dbo.tenants)
  INSERT INTO dbo.tenants (userId, roomNumber)
  SELECT id, '101' FROM dbo.users WHERE email = 'tenant@example.com';
GO
