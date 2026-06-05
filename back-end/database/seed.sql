USE motel_manager;
GO

IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email = 'owner@example.com')
  INSERT INTO dbo.users (name, email, password, role)
  VALUES ('Owner One', 'owner@example.com', 'ownerpass', 'owner');
GO

IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email = 'manager@example.com')
  INSERT INTO dbo.users (name, email, password, role)
  VALUES ('Manager One', 'manager@example.com', 'managerpass', 'manager');
GO

IF NOT EXISTS(SELECT 1 FROM dbo.users WHERE email = 'tenant@example.com')
  INSERT INTO dbo.users (name, email, password, role)
  VALUES ('Tenant One', 'tenant@example.com', 'tenantpass', 'tenant');
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
