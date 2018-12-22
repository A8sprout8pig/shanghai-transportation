create table if not exists bus_line
(
	id bigint auto_increment comment '主键',
	bus_line varchar(200) not null comment '公交线路名',
	up_going_start_station varchar(200) null comment '上行方向起点站',
	up_going_terminal_station varchar(200) null comment '上行方向终点站',
	up_going_first_time varchar(200) null comment '上行方向首班车时间',
	up_going_last_time varchar(200) null comment '上行方向末班车时间',
	up_going_station_count int null comment '上行方向站点数',
	down_going_start_station varchar(200) null comment '下行方向起点站',
	down_going_terminal_station varchar(200) null comment '下行方向终点站',
	down_going_first_time varchar(200) null comment '下行方向首班车时间',
	down_going_last_time varchar(200) null comment '下行方向末班车时间',
	down_going_station_count int null comment '下行方向站点数',
	line_length decimal(14,2) null comment '全场, 单位km',
	bus_description varchar(200) null comment '线路描述',
	bus_cost varchar(200) null comment '收费',
	bus_comment varchar(200) null comment '备注',
	is_del int null comment '逻辑删除标志符 0-未删除 1-已删除',
	create_time datetime default current_timestamp null comment '创建时间',
	update_time datetime default current_timestamp null comment '更新时间',
	constraint bus_line_pk
		primary key (id)
)
comment '公交线路信息';

create table if not exists  bus_line_exception
(
  id bigint auto_increment comment '主键',
  bus_line varchar(200) null comment '公交线路',
  exception_reason varchar(200) null comment '异常原因',
  create_time datetime default current_timestamp null comment '创建时间',
  is_del int default 0 null comment '逻辑删除标志符(是否已处理) 0-未删除(未处理) 1-已删除(已处理)',
  update_time datetime null comment '更新时间(处理时间)',
  constraint bus_line_exception_pk
    primary key (id)
)
  comment '公交数据维护异常表, 用于日志记录和批处理';