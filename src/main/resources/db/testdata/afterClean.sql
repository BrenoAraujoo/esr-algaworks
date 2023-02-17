set foreign_key_checks = 0;

drop table if exists  cidade;
drop table if exists  cozinha;
drop table if exists  estado;
drop table if exists  forma_pagamento;
drop table if exists  grupo;
drop table if exists  grupo_permissao;
drop table if exists  permissao;
drop table if exists  produto;
drop table if exists  restaurante;
drop table if exists  restaurante_forma_pagamento;
drop table if exists  usuario;
drop table if exists  usuario_grupo;
drop table if exists  restaurante_usuario_responsavel;
drop table if exists  pedido;
drop table if exists  item_pedido;
drop table if exists  flyway_schema_history;

set foreign_key_checks = 1;