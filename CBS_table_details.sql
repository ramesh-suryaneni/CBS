CREATE TABLE [cbs].[approval_status_dm] (
	approval_status_id bigint IDENTITY(1000,1),
	approval_description varchar(255) NOT NULL,
	approval_name varchar(255) NOT NULL,
  CONSTRAINT [PK_APPROVAL_STATUS_DM] PRIMARY KEY CLUSTERED
  (
  [approval_status_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[employee_mapping] (
	employee_id bigint IDENTITY(1000,1),
	employee_number_maconomy bigint NOT NULL,
	ogle_account varchar(255) NOT NULL,
  CONSTRAINT [PK_EMPLOYEE_MAPPING] PRIMARY KEY CLUSTERED
  (
  [employee_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[team] (
	team_id bigint IDENTITY(1000,1),
	team_name varchar(255) NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
  CONSTRAINT [PK_TEAM] PRIMARY KEY CLUSTERED
  (
  [team_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[approver] (
	approver_id bigint IDENTITY(1000,1),
	team_id bigint NOT NULL,
	employe_id bigint NOT NULL,
	approver_number bigint NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
  CONSTRAINT [PK_APPROVER] PRIMARY KEY CLUSTERED
  (
  [approver_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[booking] (
	booking_id bigint IDENTITY(1000,1),
	booking_description varchar(255) NOT NULL,
	team_id bigint NOT NULL,
	status_id bigint NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
  CONSTRAINT [PK_BOOKING] PRIMARY KEY CLUSTERED
  (
  [booking_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[booking_revision] (
	booking_revision_id bigint IDENTITY(2000,1),
	booking_id bigint NOT NULL,
	revision_number bigint NOT NULL,
	contractor_employee_role_id bigint NOT NULL,
	contracted_from_date datetime NOT NULL,
	contracted_to_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
	currency_id bigint NOT NULL,
	job_number bigint NOT NULL,
	changed_date datetime NOT NULL,
	rate decimal NOT NULL,
	approval_status_id bigint NOT NULL,
	agreement_document_id bigint,
	agreement_id bigint,
	contractor_signed_date datetime NOT NULL,
  CONSTRAINT [PK_BOOKING_REVISION] PRIMARY KEY CLUSTERED
  (
  [booking_revision_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[contractor_employee_role] (
	contractor_employee_role_id bigint IDENTITY(2000,1),
	role_id bigint NOT NULL,
	contractor_employee_id bigint NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_ROLE] PRIMARY KEY CLUSTERED
  (
  [contractor_employee_role_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[role_dm] (
	role_id bigint IDENTITY(3000,1),
	role_name varchar(255) NOT NULL,
	role_description varchar(255) NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
	discipline_id bigint NOT NULL,
  CONSTRAINT [PK_ROLE_DM] PRIMARY KEY CLUSTERED
  (
  [role_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[role_default_rate] (
	default_rate_id bigint IDENTITY(4000,1),
	role_id bigint NOT NULL,
	rate decimal NOT NULL,
	currency_id bigint NOT NULL,
	date_from datetime NOT NULL,
  CONSTRAINT [PK_ROLE_DEFAULT_RATE] PRIMARY KEY CLUSTERED
  (
  [default_rate_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[contractor_employee] (
	contractor_employee_id bigint IDENTITY(5000,1),
	employee_name varchar(255) NOT NULL,
	contractor_id bigint NOT NULL,
	contact_details varchar(255) NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
	known_as varchar(255) NOT NULL,
	employee_id bigint NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE] PRIMARY KEY CLUSTERED
  (
  [contractor_employee_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[contractor] (
	contractor_id bigint IDENTITY(6000,1),
	contractor_name varchar(255) NOT NULL,
	company_type varchar(255) NOT NULL,
	contact_details varchar(255) NOT NULL,
	changed_date datetime NOT NULL,
	changed_by varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_CONTRACTOR] PRIMARY KEY CLUSTERED
  (
  [contractor_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[currency_dm] (
	currency_id bigint IDENTITY(100,1),
	company_name varchar(255) NOT NULL,
	contractor_number bigint NOT NULL,
  CONSTRAINT [PK_CURRENCY_DM] PRIMARY KEY CLUSTERED
  (
  [currency_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[access_rights_tbc] (
	user_name varchar(255) NOT NULL,
	fields_tbc varchar(255) NOT NULL,
  CONSTRAINT [PK_ACCESS_RIGHTS_TBC] PRIMARY KEY CLUSTERED
  (
  [user_name] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[contractor_employee_default_rate] (
	rate_id bigint IDENTITY(6000,1),
	contractor_employee_id bigint NOT NULL,
	rate decimal NOT NULL,
	currency_id bigint NOT NULL,
	date_from datetime NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_DEFAULT_RATE] PRIMARY KEY CLUSTERED
  (
  [rate_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[contractor_employee_ratings] (
	contractor_employee_ratings_id bigint IDENTITY(7000,1),
	contractor_employee_id bigint NOT NULL,
	rating_given decimal NOT NULL,
	rating_given_by varchar(255) NOT NULL,
	rating_given_date datetime NOT NULL,
	booking_id bigint NOT NULL,
	description varchar(255) NOT NULL,
	status varchar(255) NOT NULL,
  CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_RATINGS] PRIMARY KEY CLUSTERED
  (
  [contractor_employee_ratings_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)

CREATE TABLE [cbs].[discipline] (
     discipline_id bigint NOT NULL IDENTITY(8000,1),
     discipline_name varchar(255) NOT NULL,
     discipline_description varchar(255) NOT NULL,
  CONSTRAINT [PK_DISCIPLINE] PRIMARY KEY CLUSTERED
  (
  [discipline_id] ASC
  ) WITH (IGNORE_DUP_KEY = OFF)

)


ALTER TABLE [cbs].[approver] WITH CHECK ADD CONSTRAINT [approver_fk0] FOREIGN KEY ([team_id]) REFERENCES [team]([team_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[approver] CHECK CONSTRAINT [approver_fk0]

ALTER TABLE [cbs].[approver] WITH CHECK ADD CONSTRAINT [approver_fk1] FOREIGN KEY ([employe_id]) REFERENCES [employee_mapping]([employee_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[approver] CHECK CONSTRAINT [approver_fk1]


ALTER TABLE [cbs].[booking] WITH CHECK ADD CONSTRAINT [booking_fk0] FOREIGN KEY ([team_id]) REFERENCES [team]([team_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[booking] CHECK CONSTRAINT [booking_fk0]

ALTER TABLE [cbs].[booking] WITH CHECK ADD CONSTRAINT [booking_fk1] FOREIGN KEY ([status_id]) REFERENCES [approval_status_dm]([approval_status_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[booking] CHECK CONSTRAINT [booking_fk1]


ALTER TABLE [cbs].[booking_revision] WITH CHECK ADD CONSTRAINT [booking_revision_fk0] FOREIGN KEY ([booking_id]) REFERENCES [booking]([booking_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[booking_revision] CHECK CONSTRAINT [booking_revision_fk0]

ALTER TABLE [cbs].[booking_revision] WITH CHECK ADD CONSTRAINT [booking_revision_fk1] FOREIGN KEY ([currency_id]) REFERENCES [currency_dm]([currency_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[booking_revision] CHECK CONSTRAINT [booking_revision_fk1]

ALTER TABLE [cbs].[booking_revision] WITH CHECK ADD CONSTRAINT [booking_revision_fk2] FOREIGN KEY ([approval_status_id]) REFERENCES [approval_status_dm]([approval_status_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[booking_revision] CHECK CONSTRAINT [booking_revision_fk2]


ALTER TABLE [cbs].[contractor_employee_role] WITH CHECK ADD CONSTRAINT [contractor_employee_role_fk0] FOREIGN KEY ([role_id]) REFERENCES [role_dm]([role_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee_role] CHECK CONSTRAINT [contractor_employee_role_fk0]

ALTER TABLE [cbs].[contractor_employee_role] WITH CHECK ADD CONSTRAINT [contractor_employee_role_fk1] FOREIGN KEY ([contractor_employee_id]) REFERENCES [contractor_employee]([contractor_employee_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee_role] CHECK CONSTRAINT [contractor_employee_role_fk1]



ALTER TABLE [cbs].[role_default_rate] WITH CHECK ADD CONSTRAINT [role_default_rate_fk0] FOREIGN KEY ([role_id]) REFERENCES [role_dm]([role_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[role_default_rate] CHECK CONSTRAINT [role_default_rate_fk0]

ALTER TABLE [cbs].[role_default_rate] WITH CHECK ADD CONSTRAINT [role_default_rate_fk1] FOREIGN KEY ([currency_id]) REFERENCES [currency_dm]([currency_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[role_default_rate] CHECK CONSTRAINT [role_default_rate_fk1]


ALTER TABLE [cbs].[contractor_employee] WITH CHECK ADD CONSTRAINT [contractor_employee_fk0] FOREIGN KEY ([contractor_id]) REFERENCES [contractor]([contractor_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee] CHECK CONSTRAINT [contractor_employee_fk0]



ALTER TABLE [cbs].[currency_dm] WITH CHECK ADD CONSTRAINT [currency_dm_fk0] FOREIGN KEY ([contractor_number]) REFERENCES [contractor]([contractor_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[currency_dm] CHECK CONSTRAINT [currency_dm_fk0]



ALTER TABLE [cbs].[contractor_employee_default_rate] WITH CHECK ADD CONSTRAINT [contractor_employee_default_rate_fk0] FOREIGN KEY ([contractor_employee_id]) REFERENCES [contractor_employee]([contractor_employee_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee_default_rate] CHECK CONSTRAINT [contractor_employee_default_rate_fk0]

ALTER TABLE [cbs].[contractor_employee_default_rate] WITH CHECK ADD CONSTRAINT [contractor_employee_default_rate_fk1] FOREIGN KEY ([currency_id]) REFERENCES [currency_dm]([currency_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee_default_rate] CHECK CONSTRAINT [contractor_employee_default_rate_fk1]


ALTER TABLE [cbs].[contractor_employee_ratings] WITH CHECK ADD CONSTRAINT [contractor_employee_ratings_fk0] FOREIGN KEY ([contractor_employee_id]) REFERENCES [contractor_employee]([contractor_employee_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee_ratings] CHECK CONSTRAINT [contractor_employee_ratings_fk0]

ALTER TABLE [cbs].[contractor_employee_ratings] WITH CHECK ADD CONSTRAINT [contractor_employee_ratings_fk1] FOREIGN KEY ([booking_id]) REFERENCES [booking]([booking_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[contractor_employee_ratings] CHECK CONSTRAINT [contractor_employee_ratings_fk1]


ALTER TABLE [cbs].[role_dm] WITH CHECK ADD CONSTRAINT [role_dm_fk0] FOREIGN KEY ([discpline_id]) REFERENCES [discipline]([discipline_id])
ON UPDATE CASCADE

ALTER TABLE [cbs].[role_dm] CHECK CONSTRAINT [role_dm_fk0]

