CREATE TABLE [approval_types] (
	approval_status_id bigint NOT NULL,
	approval_description varchar(255) NOT NULL,
  CONSTRAINT [PK_APPROVAL_TYPES] PRIMARY KEY CLUSTERED
  (
  [approval_status_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [employee_mapping] (
	employee_id bigint NOT NULL,
	employee_number_maconomy bigint NOT NULL,
	google_account varchar(255) NOT NULL,
  CONSTRAINT [PK_EMPLOYEE_MAPPING] PRIMARY KEY CLUSTERED
  (
  [employee_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [team] (
	team_id bigint NOT NULL,
	team_name varchar(255) NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
  CONSTRAINT [PK_TEAM] PRIMARY KEY CLUSTERED
  (
  [team_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [approver] (
	approver_id bigint NOT NULL,
	team_id bigint NOT NULL,
	employe_id bigint NOT NULL,
	approver_number bigint NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
  CONSTRAINT [PK_APPROVER] PRIMARY KEY CLUSTERED
  (
  [approver_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [booking] (
	booking_id bigint NOT NULL,
	booking_description varchar(255) NOT NULL,
	team_id bigint NOT NULL,
	status varchar(255) NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
  CONSTRAINT [PK_BOOKING] PRIMARY KEY CLUSTERED
  (
  [booking_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [booking_revision] (
	booking_revision_id bigint NOT NULL,
	booking_id bigint NOT NULL,
	revision_number bigint NOT NULL,
	contractor_employee_role_id bigint NOT NULL,
	contracted_from_date date NOT NULL,
	contracted_to_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
	currency_id bigint NOT NULL,
	job_number bigint NOT NULL,
	changed_date date NOT NULL,
	rate decimal NOT NULL,
	approval_status_id bigint NOT NULL,
  CONSTRAINT [PK_BOOKING_REVISION] PRIMARY KEY CLUSTERED
  (
  [booking_revision_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [contractor_employee_role] (
	contractor_employee_role_id bigint NOT NULL,
	role_id bigint NOT NULL,
	contractor_employee_id bigint NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_ROLE] PRIMARY KEY CLUSTERED
  (
  [contractor_employee_role_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [role] (
	role_id bigint NOT NULL,
	role_name varchar(255) NOT NULL,
	role_description varchar(255) NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_ROLE] PRIMARY KEY CLUSTERED
  (
  [role_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [role_default_rate] (
	default_rate_id bigint NOT NULL,
	role_id bigint NOT NULL,
	rate decimal NOT NULL,
	currency_id bigint NOT NULL,
	date_from date NOT NULL,
  CONSTRAINT [PK_ROLE_DEFAULT_RATE] PRIMARY KEY CLUSTERED
  (
  [default_rate_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [contractor_employee] (
	contractor_employee_id bigint NOT NULL,
	employee_name varchar(255) NOT NULL,
	contractor_id bigint NOT NULL,
	contact_details varchar(255) NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
	known_as varchar(255) NOT NULL,
	employee_id bigint NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE] PRIMARY KEY CLUSTERED
  (
  [contractor_employee_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [contractor] (
	contractor_id bigint NOT NULL,
	contractor_name varchar(255) NOT NULL,
	company_type varchar(255) NOT NULL,
	contact_details varchar(255) NOT NULL,
	changed_date date NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_CONTRACTOR] PRIMARY KEY CLUSTERED
  (
  [contractor_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [currency] (
	currency_id bigint NOT NULL,
	company_name varchar(255) NOT NULL,
	company_number bigint NOT NULL,
  CONSTRAINT [PK_CURRENCY] PRIMARY KEY CLUSTERED
  (
  [currency_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [access_rights_tbc] (
	user_name varchar(255) NOT NULL,
	fields_tbc varchar(255) NOT NULL,
  CONSTRAINT [PK_ACCESS_RIGHTS_TBC] PRIMARY KEY CLUSTERED
  (
  [user_name] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [contractor_employee_default_rate] (
	rate_id bigint NOT NULL,
	contractor_employee_id bigint NOT NULL,
	rate decimal NOT NULL,
	currency_id bigint NOT NULL,
	date_from date NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_DEFAULT_RATE] PRIMARY KEY CLUSTERED
  (
  [rate_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO
CREATE TABLE [contractor_employee_ratings] (
	contractor_employee_ratings_id bigint NOT NULL,
	contractor_employee_id bigint NOT NULL,
	rating_given decimal NOT NULL,
	rating_given_by varchar(255) NOT NULL,
	rating_given_date date NOT NULL,
	booking_id bigint NOT NULL,
	description varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_RATINGS] PRIMARY KEY CLUSTERED
  (
  [contractor_employee_ratings_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)
GO



ALTER TABLE [approver] WITH CHECK ADD CONSTRAINT [approver_fk0] FOREIGN KEY ([team_id]) REFERENCES [team]([team_id])
ON UPDATE CASCADE
GO
ALTER TABLE [approver] CHECK CONSTRAINT [approver_fk0]
GO
ALTER TABLE [approver] WITH CHECK ADD CONSTRAINT [approver_fk1] FOREIGN KEY ([employe_id]) REFERENCES [employee_mapping]([employee_id])
ON UPDATE CASCADE
GO
ALTER TABLE [approver] CHECK CONSTRAINT [approver_fk1]
GO
ALTER TABLE [approver] WITH CHECK ADD CONSTRAINT [approver_fk2] FOREIGN KEY ([changed_by]) REFERENCES [team]([changed_by])
ON UPDATE CASCADE
GO
ALTER TABLE [approver] CHECK CONSTRAINT [approver_fk2]
GO

ALTER TABLE [booking] WITH CHECK ADD CONSTRAINT [booking_fk0] FOREIGN KEY ([team_id]) REFERENCES [team]([team_id])
ON UPDATE CASCADE
GO
ALTER TABLE [booking] CHECK CONSTRAINT [booking_fk0]
GO

ALTER TABLE [booking_revision] WITH CHECK ADD CONSTRAINT [booking_revision_fk0] FOREIGN KEY ([booking_id]) REFERENCES [booking]([booking_id])
ON UPDATE CASCADE
GO
ALTER TABLE [booking_revision] CHECK CONSTRAINT [booking_revision_fk0]
GO
ALTER TABLE [booking_revision] WITH CHECK ADD CONSTRAINT [booking_revision_fk1] FOREIGN KEY ([currency_id]) REFERENCES [currency]([currency_id])
ON UPDATE CASCADE
GO
ALTER TABLE [booking_revision] CHECK CONSTRAINT [booking_revision_fk1]
GO
ALTER TABLE [booking_revision] WITH CHECK ADD CONSTRAINT [booking_revision_fk2] FOREIGN KEY ([approval_status_id]) REFERENCES [approval_types]([approval_status_id])
ON UPDATE CASCADE
GO
ALTER TABLE [booking_revision] CHECK CONSTRAINT [booking_revision_fk2]
GO

ALTER TABLE [contractor_employee_role] WITH CHECK ADD CONSTRAINT [contractor_employee_role_fk0] FOREIGN KEY ([role_id]) REFERENCES [role]([role_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee_role] CHECK CONSTRAINT [contractor_employee_role_fk0]
GO
ALTER TABLE [contractor_employee_role] WITH CHECK ADD CONSTRAINT [contractor_employee_role_fk1] FOREIGN KEY ([contractor_employee_id]) REFERENCES [contractor_employee]([contractor_employee_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee_role] CHECK CONSTRAINT [contractor_employee_role_fk1]
GO


ALTER TABLE [role_default_rate] WITH CHECK ADD CONSTRAINT [role_default_rate_fk0] FOREIGN KEY ([role_id]) REFERENCES [role]([role_id])
ON UPDATE CASCADE
GO
ALTER TABLE [role_default_rate] CHECK CONSTRAINT [role_default_rate_fk0]
GO
ALTER TABLE [role_default_rate] WITH CHECK ADD CONSTRAINT [role_default_rate_fk1] FOREIGN KEY ([currency_id]) REFERENCES [currency]([currency_id])
ON UPDATE CASCADE
GO
ALTER TABLE [role_default_rate] CHECK CONSTRAINT [role_default_rate_fk1]
GO

ALTER TABLE [contractor_employee] WITH CHECK ADD CONSTRAINT [contractor_employee_fk0] FOREIGN KEY ([contractor_id]) REFERENCES [contractor]([contractor_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee] CHECK CONSTRAINT [contractor_employee_fk0]
GO




ALTER TABLE [contractor_employee_default_rate] WITH CHECK ADD CONSTRAINT [contractor_employee_default_rate_fk0] FOREIGN KEY ([contractor_employee_id]) REFERENCES [contractor_employee]([contractor_employee_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee_default_rate] CHECK CONSTRAINT [contractor_employee_default_rate_fk0]
GO
ALTER TABLE [contractor_employee_default_rate] WITH CHECK ADD CONSTRAINT [contractor_employee_default_rate_fk1] FOREIGN KEY ([currency_id]) REFERENCES [currency]([currency_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee_default_rate] CHECK CONSTRAINT [contractor_employee_default_rate_fk1]
GO

ALTER TABLE [contractor_employee_ratings] WITH CHECK ADD CONSTRAINT [contractor_employee_ratings_fk0] FOREIGN KEY ([contractor_employee_id]) REFERENCES [contractor_employee]([contractor_employee_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee_ratings] CHECK CONSTRAINT [contractor_employee_ratings_fk0]
GO
ALTER TABLE [contractor_employee_ratings] WITH CHECK ADD CONSTRAINT [contractor_employee_ratings_fk1] FOREIGN KEY ([booking_id]) REFERENCES [booking]([booking_id])
ON UPDATE CASCADE
GO
ALTER TABLE [contractor_employee_ratings] CHECK CONSTRAINT [contractor_employee_ratings_fk1]
GO

