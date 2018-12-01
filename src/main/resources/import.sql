INSERT INTO teste.dbo.user_table (id, company_id, is_enabled, is_visible, password, username) VALUES ('teste', 'teste', 1, 1, 'mypassword', 'myuser')

INSERT INTO teste.dbo.security_group (id, company_id, description, name, removed) VALUES (1, 'teste', 'primeiro teste', 'testando', 0)

INSERT INTO teste.dbo.custom_client_details (id, access_token_validity_seconds, auto_approve, client_id, client_secret, refresh_token_validity_seconds, scoped, secret_required) VALUES ('someId', 50000, 0, 'android-client', 'android-secret', 50000, 0, 1)

INSERT INTO teste.dbo.scope (scope) VALUES ('read')
INSERT INTO teste.dbo.scope (scope) VALUES ('write')
INSERT INTO teste.dbo.scope (scope) VALUES ('trust')

INSERT INTO teste.dbo.resource_id (nome) VALUES ('resource_id')

INSERT INTO teste.dbo.authorized_grant_type (authorized_grant_type) VALUES ('password')
INSERT INTO teste.dbo.authorized_grant_type (authorized_grant_type) VALUES ('authorization_code')
INSERT INTO teste.dbo.authorized_grant_type (authorized_grant_type) VALUES ('refresh_token')
INSERT INTO teste.dbo.authorized_grant_type (authorized_grant_type) VALUES ('implicit')

INSERT INTO teste.dbo.authority (authority) VALUES ('TESTE')

INSERT INTO teste.dbo.custom_client_scope (custom_client_id, scope_id) VALUES ('someId', 1)
INSERT INTO teste.dbo.custom_client_scope (custom_client_id, scope_id) VALUES ('someId', 2)
INSERT INTO teste.dbo.custom_client_scope (custom_client_id, scope_id) VALUES ('someId', 3)

INSERT INTO teste.dbo.custom_client_resource_id (custom_client_id, resource_id_id) VALUES ('someId', 1)

INSERT INTO teste.dbo.custom_client_authorized_grant_type (custom_client_id, authorized_grant_type_id) VALUES ('someId', 1)
INSERT INTO teste.dbo.custom_client_authorized_grant_type (custom_client_id, authorized_grant_type_id) VALUES ('someId', 2)
INSERT INTO teste.dbo.custom_client_authorized_grant_type (custom_client_id, authorized_grant_type_id) VALUES ('someId', 3)
INSERT INTO teste.dbo.custom_client_authorized_grant_type (custom_client_id, authorized_grant_type_id) VALUES ('someId', 4)

INSERT INTO teste.dbo.custom_client_authority (custom_client_id, authority_id) VALUES ('someId', 1)