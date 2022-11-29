-- cria tabela
create table estado (
id bigint not null auto_increment,
nome varchar(80) not null,
primary key(id)

)engine=InnoDB default charset=utf8;

-- Insere os nomes de estados provenientes da tabela cidade na tabela estado
insert into estado (nome) select distinct nome_estado from cidade;

-- Add coluna de referência de estado na tabela cidade
alter table cidade add column estado_id bigint not null;

-- Atualiza coluna estado_id de cidade baseado no join entre cidade.estado_nome e estado.nome
update cidade c inner join estado e on c.nome_estado = e.nome
set c.estado_id = e.id;

-- Criando fk de estado em cidade
alter table cidade add constraint fk_cidade_estado
foreign key (estado_id) references estado(id);

-- Remove coluna nome_estado de cidade
alter table cidade drop column nome_estado;
-- Muda nome da coluna
alter table cidade change nome_cidade nome varchar(80) not null;