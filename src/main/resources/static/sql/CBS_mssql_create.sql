/****** Object:  Schema [cbs]    Script Date: 13-03-2020 19:38:17 ******/
CREATE SCHEMA [cbs]
GO
/****** Object:  Table [cbs].[active_company_teams]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[active_company_teams](
	[id] [bigint] NOT NULL,
	[company_number] [bigint] NOT NULL,
	[team_id] [bigint] NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_active_company_teams] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[approval_status_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[approval_status_dm](
	[approval_status_id] [bigint] IDENTITY(1000,1) NOT NULL,
	[approval_description] [varchar](255) NULL,
	[approval_name] [varchar](255) NULL,
	[changed_by] [varchar](255) NULL,
	[changed_date] [datetime] NULL,
 CONSTRAINT [PK_APPROVAL_STATUS_DM] PRIMARY KEY CLUSTERED 
(
	[approval_status_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[approver]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[approver](
	[approver_id] [bigint] IDENTITY(1000,1) NOT NULL,
	[team_id] [bigint] NULL,
	[employe_id] [bigint] NULL,
	[approver_order] [bigint] NOT NULL,
	[changed_date] [datetime] NOT NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_APPROVER] PRIMARY KEY CLUSTERED 
(
	[approver_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[approver_override_jobs]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[approver_override_jobs](
	[override_id] [bigint] NOT NULL,
	[job_number] [varchar](255) NOT NULL,
	[employee_id] [bigint] NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_approver_override_jobs] PRIMARY KEY CLUSTERED 
(
	[override_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[booking]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[booking](
	[booking_id] [bigint] IDENTITY(1000,1) NOT NULL,
	[booking_description] [varchar](255) NULL,
	[team_id] [bigint] NOT NULL,
	[status_id] [bigint] NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_BOOKING] PRIMARY KEY CLUSTERED 
(
	[booking_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[booking_revision]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[booking_revision](
	[booking_revision_id] [bigint] IDENTITY(2000,1) NOT NULL,
	[booking_id] [bigint] NOT NULL,
	[revision_number] [bigint] NOT NULL,
	[contractor_employee_role_id] [bigint] NULL,
	[contracted_from_date] [datetime] NULL,
	[contracted_to_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
	[currency_id] [bigint] NULL,
	[job_number] [varchar](255) NULL,
	[changed_date] [datetime] NULL,
	[rate] [decimal](18, 0) NULL,
	[approval_status_id] [bigint] NULL,
	[agreement_document_id] [varchar](255) NULL,
	[agreement_id] [varchar](255) NULL,
	[contractor_signed_date] [datetime] NULL,
	[inside_ir35] [char](1) NULL,
	[contractor_employee_name] [varchar](255) NULL,
	[known_as] [varchar](255) NULL,
	[employee_contact_details] [varchar](255) NULL,
	[contractor_id] [bigint] NULL,
	[contractor_type] [varchar](255) NULL,
	[contractor_name] [varchar](255) NULL,
	[contractor_contact_details] [varchar](500) NULL,
	[office_id] [varchar](255) NULL,
	[office_description] [varchar](255) NULL,
	[contractor_total_available_days] [bigint] NULL,
	[contractor_total_working_days] [bigint] NULL,
	[contract_amount_beforetax] [decimal](18, 0) NULL,
	[contract_amount_aftertax] [decimal](18, 0) NULL,
	[employer_tax_percent] [decimal](18, 0) NULL,
 CONSTRAINT [PK_BOOKING_REVISION] PRIMARY KEY CLUSTERED 
(
	[booking_revision_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[booking_work_tasks]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[booking_work_tasks](
	[booking_work_id] [bigint] NOT NULL,
	[booking_revision_id] [bigint] NOT NULL,
	[task_name] [varchar](255) NULL,
	[task_delivery_date] [date] NULL,
	[task_date_rate] [decimal](18, 0) NULL,
	[task_total_days] [bigint] NULL,
	[task_total_amount] [decimal](18, 0) NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_booking_work_tasks] PRIMARY KEY CLUSTERED 
(
	[booking_work_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[company]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[company](
	[company_number] [bigint] IDENTITY(1000,1) NOT NULL,
	[region_id] [bigint] NULL,
	[company_description] [varchar](255) NULL,
	[currency_id] [bigint] NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_company] PRIMARY KEY CLUSTERED 
(
	[company_number] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[config]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[config](
	[config_id] [bigint] IDENTITY(1000,1) NOT NULL,
	[key_name] [varchar](255) NOT NULL,
	[key_value] [varchar](255) NOT NULL,
	[changed_date] [datetime] NULL,
	[key_description] [varchar](255) NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_CONFIG_DM] PRIMARY KEY CLUSTERED 
(
	[config_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[contractor]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[contractor](
	[contractor_id] [bigint] IDENTITY(6000,1) NOT NULL,
	[contractor_name] [varchar](255) NOT NULL,
	[company_type] [varchar](255) NOT NULL,
	[contact_details] [varchar](255) NULL,
	[changed_date] [datetime] NOT NULL,
	[changed_by] [varchar](255) NULL,
	[status] [varchar](255) NOT NULL,
 CONSTRAINT [PK_CONTRACTOR] PRIMARY KEY CLUSTERED 
(
	[contractor_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[contractor_employee]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[contractor_employee](
	[contractor_employee_id] [bigint] IDENTITY(5000,1) NOT NULL,
	[employee_name] [varchar](255) NOT NULL,
	[contractor_id] [bigint] NOT NULL,
	[contact_details] [varchar](255) NULL,
	[changed_date] [datetime] NOT NULL,
	[changed_by] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[known_as] [varchar](255) NULL,
	[employee_id] [bigint] NOT NULL,
 CONSTRAINT [PK_CONTRACTOR_EMPLOYEE] PRIMARY KEY CLUSTERED 
(
	[contractor_employee_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[contractor_employee_default_rate]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[contractor_employee_default_rate](
	[rate_id] [bigint] IDENTITY(6000,1) NOT NULL,
	[contractor_employee_id] [bigint] NOT NULL,
	[rate] [decimal](18, 0) NOT NULL,
	[currency_id] [bigint] NULL,
	[date_from] [datetime] NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_DEFAULT_RATE] PRIMARY KEY CLUSTERED 
(
	[rate_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[contractor_employee_ratings]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[contractor_employee_ratings](
	[contractor_employee_ratings_id] [bigint] IDENTITY(7000,1) NOT NULL,
	[contractor_employee_id] [bigint] NOT NULL,
	[rating_given] [decimal](18, 0) NOT NULL,
	[rating_given_by] [varchar](255) NULL,
	[rating_given_date] [datetime] NULL,
	[booking_id] [bigint] NOT NULL,
	[description] [varchar](255) NULL,
	[status] [varchar](255) NULL,
 CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_RATINGS] PRIMARY KEY CLUSTERED 
(
	[contractor_employee_ratings_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[contractor_employee_role]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[contractor_employee_role](
	[contractor_employee_role_id] [bigint] IDENTITY(2000,1) NOT NULL,
	[role_id] [bigint] NOT NULL,
	[contractor_employee_id] [bigint] NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
	[status] [varchar](255) NULL,
 CONSTRAINT [PK_CONTRACTOR_EMPLOYEE_ROLE] PRIMARY KEY CLUSTERED 
(
	[contractor_employee_role_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[contractor_monthly_workdays]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[contractor_monthly_workdays](
	[work_days_id] [bigint] NOT NULL,
	[booking_revision_id] [bigint] NOT NULL,
	[month_name] [varchar](255) NOT NULL,
	[month_working_days] [bigint] NOT NULL,
	[changed_by] [varchar](255) NULL,
	[changed_datetime] [datetime] NULL,
 CONSTRAINT [PK_contractor_monthly_workdays] PRIMARY KEY CLUSTERED 
(
	[work_days_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[country_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[country_dm](
	[country_id] [bigint] IDENTITY(7000,1) NOT NULL,
	[country_name] [varchar](255) NOT NULL,
	[country_description] [varchar](255) NULL,
	[created_date] [datetime] NULL,
	[created_by] [varchar](255) NULL,
 CONSTRAINT [PK_COUNTRY_DM] PRIMARY KEY CLUSTERED 
(
	[country_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[currency_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[currency_dm](
	[currency_id] [bigint] IDENTITY(100,1) NOT NULL,
	[currency_code] [varchar](255) NOT NULL,
	[currency_name] [varchar](255) NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_CURRENCY_DM] PRIMARY KEY CLUSTERED 
(
	[currency_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[discipline]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[discipline](
	[discipline_id] [bigint] IDENTITY(8000,1) NOT NULL,
	[discipline_name] [varchar](255) NOT NULL,
	[discipline_description] [varchar](255) NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_DISCIPLINE] PRIMARY KEY CLUSTERED 
(
	[discipline_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[employee_mapping]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[employee_mapping](
	[employee_id] [bigint] IDENTITY(1000,1) NOT NULL,
	[employee_number_maconomy] [bigint] NOT NULL,
	[ogle_account] [varchar](255) NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_EMPLOYEE_MAPPING] PRIMARY KEY CLUSTERED 
(
	[employee_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[employee_permissions]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[employee_permissions](
	[emp_permission_id] [bigint] NOT NULL,
	[employee_id] [bigint] NOT NULL,
	[company_number] [bigint] NOT NULL,
	[permission_id] [bigint] NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_employee_permissions] PRIMARY KEY CLUSTERED 
(
	[emp_permission_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[office_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[office_dm](
	[office_id] [bigint] IDENTITY(8000,1) NOT NULL,
	[office_name] [varchar](255) NOT NULL,
	[office_description] [varchar](255) NOT NULL,
	[created_date] [datetime] NULL,
	[created_by] [varchar](255) NULL,
	[inside_ir35] [char](1) NULL,
 CONSTRAINT [PK_OFFICE_DM] PRIMARY KEY CLUSTERED 
(
	[office_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[permission]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[permission](
	[permission_id] [bigint] NOT NULL,
	[permission_name] [varchar](255) NOT NULL,
	[permission_description] [varchar](255) NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_permission] PRIMARY KEY CLUSTERED 
(
	[permission_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[reasons_for_recruiting]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[reasons_for_recruiting](
	[reason_id] [bigint] NOT NULL,
	[reason_name] [varchar](255) NOT NULL,
	[reason_description] [varchar](255) NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_reasons_for_recruiting] PRIMARY KEY CLUSTERED 
(
	[reason_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[region]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[region](
	[region_id] [bigint] NOT NULL,
	[region_name] [varchar](255) NULL,
	[region_description] [varchar](255) NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_region] PRIMARY KEY CLUSTERED 
(
	[region_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[role_default_rate]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[role_default_rate](
	[default_rate_id] [bigint] IDENTITY(4000,1) NOT NULL,
	[role_id] [bigint] NOT NULL,
	[rate] [decimal](18, 0) NOT NULL,
	[currency_id] [bigint] NULL,
	[date_from] [datetime] NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_ROLE_DEFAULT_RATE] PRIMARY KEY CLUSTERED 
(
	[default_rate_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[role_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[role_dm](
	[role_id] [bigint] IDENTITY(3000,1) NOT NULL,
	[role_name] [varchar](255) NULL,
	[role_description] [varchar](255) NULL,
	[changed_date] [datetime] NOT NULL,
	[changed_by] [varchar](255) NOT NULL,
	[discipline_id] [bigint] NOT NULL,
	[inside_ir35] [char](1) NULL,
	[status] [char](1) NULL,
 CONSTRAINT [PK_ROLE_DM] PRIMARY KEY CLUSTERED 
(
	[role_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[supplier_type_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[supplier_type_dm](
	[id] [bigint] NOT NULL,
	[name] [varchar](255) NOT NULL,
	[description] [nchar](10) NULL,
 CONSTRAINT [PK_supplier_type_dm] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[tax_rate_dm]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[tax_rate_dm](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[rate_percent] [decimal](18, 0) NULL,
	[tax_description] [varchar](255) NULL,
 CONSTRAINT [PK_tax_rate_dm] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [cbs].[team]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [cbs].[team](
	[team_id] [bigint] IDENTITY(1000,1) NOT NULL,
	[team_name] [varchar](255) NOT NULL,
	[changed_date] [datetime] NULL,
	[changed_by] [varchar](255) NULL,
 CONSTRAINT [PK_TEAM] PRIMARY KEY CLUSTERED 
(
	[team_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sysdiagrams]    Script Date: 13-03-2020 19:38:17 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[sysdiagrams](
	[name] [sysname] NOT NULL,
	[principal_id] [int] NOT NULL,
	[diagram_id] [int] IDENTITY(1,1) NOT NULL,
	[version] [int] NULL,
	[definition] [varbinary](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[diagram_id] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UK_principal_name] UNIQUE NONCLUSTERED 
(
	[principal_id] ASC,
	[name] ASC
)WITH (STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
ALTER TABLE [cbs].[active_company_teams] ADD  CONSTRAINT [DF_active_company_teams_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[approval_status_dm] ADD  CONSTRAINT [DF_approval_status_dm_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[approver] ADD  CONSTRAINT [DF_approver_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[approver_override_jobs] ADD  CONSTRAINT [DF_approver_override_jobs_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[booking] ADD  CONSTRAINT [DF_booking_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[booking_work_tasks] ADD  CONSTRAINT [DF_booking_work_tasks_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[company] ADD  CONSTRAINT [DF_company_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[contractor] ADD  CONSTRAINT [DF_contractor_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[contractor_employee] ADD  CONSTRAINT [DF_contractor_employee_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[contractor_employee_ratings] ADD  CONSTRAINT [DF_contractor_employee_ratings_rating_given_date]  DEFAULT (getdate()) FOR [rating_given_date]
GO
ALTER TABLE [cbs].[contractor_employee_role] ADD  CONSTRAINT [DF_contractor_employee_role_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[contractor_monthly_workdays] ADD  CONSTRAINT [DF_contractor_monthly_workdays_changed_datetime]  DEFAULT (getdate()) FOR [changed_datetime]
GO
ALTER TABLE [cbs].[discipline] ADD  CONSTRAINT [DF_discipline_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[employee_mapping] ADD  CONSTRAINT [DF_employee_mapping_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[employee_permissions] ADD  CONSTRAINT [DF_employee_permissions_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[office_dm] ADD  CONSTRAINT [DF_office_dm_created_date]  DEFAULT (getdate()) FOR [created_date]
GO
ALTER TABLE [cbs].[permission] ADD  CONSTRAINT [DF_permission_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[reasons_for_recruiting] ADD  CONSTRAINT [DF_reasons_for_recruiting_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[region] ADD  CONSTRAINT [DF_region_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[role_default_rate] ADD  CONSTRAINT [DF_role_default_rate_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[role_dm] ADD  CONSTRAINT [DF_role_dm_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[team] ADD  CONSTRAINT [DF_team_changed_date]  DEFAULT (getdate()) FOR [changed_date]
GO
ALTER TABLE [cbs].[active_company_teams]  WITH CHECK ADD  CONSTRAINT [FK_active_company_teams_company] FOREIGN KEY([company_number])
REFERENCES [cbs].[company] ([company_number])
GO
ALTER TABLE [cbs].[active_company_teams] CHECK CONSTRAINT [FK_active_company_teams_company]
GO
ALTER TABLE [cbs].[active_company_teams]  WITH CHECK ADD  CONSTRAINT [FK_active_company_teams_team] FOREIGN KEY([team_id])
REFERENCES [cbs].[team] ([team_id])
GO
ALTER TABLE [cbs].[active_company_teams] CHECK CONSTRAINT [FK_active_company_teams_team]
GO
ALTER TABLE [cbs].[approver_override_jobs]  WITH CHECK ADD  CONSTRAINT [FK_approver_override_jobs_employee_mapping] FOREIGN KEY([employee_id])
REFERENCES [cbs].[employee_mapping] ([employee_id])
GO
ALTER TABLE [cbs].[approver_override_jobs] CHECK CONSTRAINT [FK_approver_override_jobs_employee_mapping]
GO
ALTER TABLE [cbs].[booking]  WITH CHECK ADD  CONSTRAINT [FK_booking_approval_status_dm] FOREIGN KEY([status_id])
REFERENCES [cbs].[approval_status_dm] ([approval_status_id])
GO
ALTER TABLE [cbs].[booking] CHECK CONSTRAINT [FK_booking_approval_status_dm]
GO
ALTER TABLE [cbs].[booking]  WITH CHECK ADD  CONSTRAINT [FK_booking_team] FOREIGN KEY([team_id])
REFERENCES [cbs].[team] ([team_id])
GO
ALTER TABLE [cbs].[booking] CHECK CONSTRAINT [FK_booking_team]
GO
ALTER TABLE [cbs].[booking_work_tasks]  WITH CHECK ADD  CONSTRAINT [FK_booking_work_tasks_booking_revision] FOREIGN KEY([booking_revision_id])
REFERENCES [cbs].[booking_revision] ([booking_revision_id])
GO
ALTER TABLE [cbs].[booking_work_tasks] CHECK CONSTRAINT [FK_booking_work_tasks_booking_revision]
GO
ALTER TABLE [cbs].[company]  WITH CHECK ADD  CONSTRAINT [FK_company_currency_dm] FOREIGN KEY([currency_id])
REFERENCES [cbs].[currency_dm] ([currency_id])
GO
ALTER TABLE [cbs].[company] CHECK CONSTRAINT [FK_company_currency_dm]
GO
ALTER TABLE [cbs].[company]  WITH CHECK ADD  CONSTRAINT [FK_company_region] FOREIGN KEY([region_id])
REFERENCES [cbs].[region] ([region_id])
GO
ALTER TABLE [cbs].[company] CHECK CONSTRAINT [FK_company_region]
GO
ALTER TABLE [cbs].[contractor_monthly_workdays]  WITH CHECK ADD  CONSTRAINT [FK_contractor_monthly_workdays_booking_revision] FOREIGN KEY([booking_revision_id])
REFERENCES [cbs].[booking_revision] ([booking_revision_id])
GO
ALTER TABLE [cbs].[contractor_monthly_workdays] CHECK CONSTRAINT [FK_contractor_monthly_workdays_booking_revision]
GO
ALTER TABLE [cbs].[employee_permissions]  WITH CHECK ADD  CONSTRAINT [FK_employee_permissions_company] FOREIGN KEY([company_number])
REFERENCES [cbs].[company] ([company_number])
GO
ALTER TABLE [cbs].[employee_permissions] CHECK CONSTRAINT [FK_employee_permissions_company]
GO
ALTER TABLE [cbs].[employee_permissions]  WITH CHECK ADD  CONSTRAINT [FK_employee_permissions_employee_mapping] FOREIGN KEY([employee_id])
REFERENCES [cbs].[employee_mapping] ([employee_id])
GO
ALTER TABLE [cbs].[employee_permissions] CHECK CONSTRAINT [FK_employee_permissions_employee_mapping]
GO
ALTER TABLE [cbs].[employee_permissions]  WITH CHECK ADD  CONSTRAINT [FK_employee_permissions_permission] FOREIGN KEY([permission_id])
REFERENCES [cbs].[permission] ([permission_id])
GO
ALTER TABLE [cbs].[employee_permissions] CHECK CONSTRAINT [FK_employee_permissions_permission]
GO
