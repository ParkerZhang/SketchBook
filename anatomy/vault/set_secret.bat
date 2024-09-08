SET VAULT_ADDR=http://localhost:8200

SET VAULT_TOKEN=00000000-0000-0000-0000-000000000000


vault kv put secret/gs-vault-config example.username=demouser example.password=demopassword
vault kv put secret/gs-vault-config/cloud example.username=clouduser example.password=cloudpassword