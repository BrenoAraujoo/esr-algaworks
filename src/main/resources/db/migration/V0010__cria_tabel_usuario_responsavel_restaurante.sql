create table restaurante_usuario_responsavel(
usuario_id bigint not null,
restaurante_id bigint not null,
primary key (usuario_id, restaurante_id)
)engine=InnoDB default charset=utf8;

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_restaurante
foreign key (restaurante_id) references restaurante (id);

alter table restaurante_usuario_responsavel add constraint fk_restaurante_usuario_usuario
foreign key (usuario_id) references usuario (id);

