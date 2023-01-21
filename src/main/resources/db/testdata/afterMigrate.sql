set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;

set foreign_key_checks = 1;

alter table cidade auto_increment =1;
alter table cozinha auto_increment =1;
alter table estado auto_increment =1;
alter table forma_pagamento auto_increment =1;
alter table grupo auto_increment =1;
alter table permissao auto_increment =1;
alter table produto auto_increment =1;
alter table restaurante auto_increment =1;
alter table usuario auto_increment =1;



-- Cozinha
insert into cozinha (id, nome) values (1, 'Japonesa');
insert into cozinha (id, nome) values (2, 'Brasileira');
insert into cozinha (id, nome) values (3, 'Italiana');
insert into cozinha (id, nome) values (4, 'Indiana');
insert into cozinha (id, nome) values (5, 'Francesa');
-- Estado
insert into estado (id, nome) values(1, 'SP');
insert into estado (id, nome) values(2, 'RJ');
insert into estado (id, nome) values(3, 'MG');

-- Cidade
insert into cidade (id, nome, estado_id) values (1, 'São Paulo', 1);
insert into cidade (id, nome, estado_id) values (2, 'Rio de Janeiro', 2);
insert into cidade (id, nome, estado_id) values (3, 'Volta Redonda', 2);
insert into cidade (id, nome, estado_id) values (4, 'Belo Horizonte', 3);
insert into cidade (id, nome, estado_id) values (5, 'Uberlândia', 3);
insert into cidade (id, nome, estado_id) values (6, 'Diadema', 1);
insert into cidade (id, nome, estado_id) values (7, 'São Bernardo do Campo', 1);
-- Restaurante
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (1, 'Xing Ling',12.0, 1, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Gourmet',13.0, 4, utc_timestamp, utc_timestamp, false);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'Thai Delivery',13.0, 4, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (4, 'La massa',13.0, 3, utc_timestamp, utc_timestamp, false);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (5, 'Paris 6',0.0, 5, utc_timestamp, utc_timestamp, false);
insert into restaurante (id, nome, taxa_frete, cozinha_id, endereco_bairro, endereco_cep, endereco_logradouro, endereco_numero, endereco_cidade_id, data_cadastro, data_atualizacao, ativo) values (6, 'Feijao de corda',10.0, 2,'Jd. Guanhembu','04814-444','Predio 1','11',1, utc_timestamp, utc_timestamp, true);


-- Forma Pagamento
insert into forma_pagamento (id, descricao) values (1, 'Débito');
insert into forma_pagamento (id, descricao) values (2, 'Crédito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');



-- Permissao
insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS','Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS','Permite editar cozinhas');

-- Grupo
insert into grupo (id, nome) values(1, 'Grupo 1');
insert into grupo (id, nome) values(2, 'Grupo 2');
insert into grupo (id, nome) values(3, 'Grupo 3');

-- Grupo x Permissao
insert into grupo_permissao (grupo_id, permissao_id) values (1,1),(1,2),(2,1),(2,2),(3,1);

-- forma de pagamento x restaurantes
insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1,1), (1,2), (1,3), (2,1), (2,2), (2,3);

-- Usuarios
insert into usuario (id, nome, email, senha, data_cadastro) values (1,'Breno','breno.souza.araujo@hotmail.com', '123123',utc_timestamp);
insert into usuario (id, nome, email, senha, data_cadastro) values (2,'Rafaela','rafaela@hotmail.com','123123', utc_timestamp);

-- Usuarios x Grupos
insert into usuario_grupo (usuario_id, grupo_id) values (1,1),(1,2),(2,1);

-- Produto
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'Sushi','Sushi grelhado',120.00, 1, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'Omelete','Omelete de legumes',10.00, 0, 2);
