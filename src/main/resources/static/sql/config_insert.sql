
INSERT INTO CBS.CONFIG (key_name,key_value,changed_date,key_description)
VALUES ('ADOBE_OAUTH_BASE_URL','https://imagination.eu1.echosign.com/public/oauth',CURRENT_TIMESTAMP,'Adobe OAuth base url');

INSERT INTO CBS.CONFIG (key_name,key_value,changed_date,key_description) 
VALUES ('ADOBE_REDIRECT_URL','https://localhost:8080/auth/esignature',CURRENT_TIMESTAMP,'Adobe Redirect URL');

INSERT INTO CBS.CONFIG (key_name,key_value,changed_date,key_description) 
VALUES ('ADOBE_GRANT_TYPE','authorization_code',CURRENT_TIMESTAMP,'Adobe Token Grant Type');

INSERT INTO CBS.CONFIG (key_name,key_value,changed_date,key_description) 
VALUES ('ADOBE_CLIENT_ID','CBJCHBCAABAAVWRbJTmC9x0PxnkMHp9hgppMuDpHpJ5A',CURRENT_TIMESTAMP,'Adobe Client Id');

INSERT INTO CBS.CONFIG (key_name,key_value,changed_date,key_description) 
VALUES ('ADOBE_CLIENT_SECRET','miaYl4TrQHGd6fvQnPGzA_TscgV1MofE',CURRENT_TIMESTAMP,'Adobe Client Secret');


INSERT INTO CBS.CONFIG (key_name,key_value) VALUES('GOOGLE_ID','73478530580-60km8n2mheo2e0e5qmg57617qae6fqij.apps.googleusercontent.com');


INSERT INTO cbs.config(key_name, key_value,  changed_by)
VALUES ('MACONOMY_BASE_URL','https://maconomytouchdev.imagination.com/containers/v1/imagdev/',  'PappuRout');

INSERT INTO cbs.config(key_name, key_value,  changed_by) VALUES ('MACONOMY_USERNAME','API-YASH',  'PappuRout');

INSERT INTO cbs.config(key_name, key_value,  changed_by) VALUES ('MACONOMY_PASSWORD','2020Im@gYASH',  'PappuRout');