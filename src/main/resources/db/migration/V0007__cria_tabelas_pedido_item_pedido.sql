create table pedido(
id bigint not null auto_increment,
subtotal decimal(10,2) not null,
taxa_frete decimal(10,2) not null,
valor_total decimal(10,2) not null,

restaurante_id bigint not null,
usuario_cliente_id bigint not null,
forma_pagamento_id bigint not null,

endereco_cidade_id bigint not null,
endereco_cep varchar(9) not null,
endereco_logradouro varchar(100) not null,
endereco_numero varchar(20) not null,
endereco_complemento varchar(60) null,
endereco_bairro varchar(60) not null,

status varchar(10) not null,
data_criacao datetime not null,
data_confirmacao datetime null,
data_cancelamento datetime null,
data_entrega datetime null,

primary key (id),
constraint fk_restaurante foreign key (restaurante_id) references restaurante(id),
constraint fk_endereco_cidade foreign key (endereco_cidade_id) references cidade(id),
constraint fk_usuario_cliente foreign key (usuario_cliente_id) references usuario(id),
constraint fk_forma_pagamento foreign key (forma_pagamento_id) references forma_pagamento(id)
)engine=InnoDB default charset=utf8;


create table item_pedido(
id bigint not null auto_increment,
quantidade bigint not null,
preco_unitario decimal (10,2) not null,
preco_total decimal(10,2) not null,
observacao varchar(60) null,

primary key (id),

pedido_id bigint not null,
produto_id bigint not null,

unique key uk_item_pedido_produto (pedido_id, produto_id),
constraint fk_item_pedido foreign key (pedido_id) references pedido(id),
constraint fk_item_produto foreign key (produto_id) references produto(id)
)engine=InnoDB default charset=utf8;