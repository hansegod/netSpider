


	ResultItems信息格式

	/**************************日志结构框架**************************/
	time_stamp				日志时间戳
	log_type				日志类型
	search_key				日志唯一标识[0-9A-Za-z]{32}
	time_ms					处理耗时(ms)
	error_code				错误码
	.
	.
	.
	server_ip				服务器ip地址(日志最后一个字段，仅用于内部调使用)
	
	
	/**************************个人信息日志**************************/
	time_stamp				日志时间戳
	log_type				日志类型
	search_key				日志唯一标识[0-9A-Za-z]{32}
	time_ms					处理耗时(ms)
	error_code				错误码
	
	key						标识
	src						来源
	name					名称
	gender					性别
	ethnic					民族
	school					学校
	major					学历
	capacity				能力
	experience				经验
	industry				行业
	scale					规模
	nature					性质
	position				职位
	type					类型
	salary					薪资
	location				地址
	expect_industry			行业*
	expect_scale			规模*
	expect_nature			性质*
	expect_position			职位*
	expect_type				类型*
	expect_salary			薪资*
	expect_location			地址*
	tag						标签
	
	server_ip				服务器ip地址(日志最后一个字段，仅用于内部调使用)
	
	
	/**************************公司信息日志**************************/
	time_stamp				日志时间戳
	log_type				日志类型
	search_key				日志唯一标识[0-9A-Za-z]{32}
	time_ms					处理耗时(ms)
	error_code				错误码
	
	key						标识
	src						来源
	name					名称
	industry				行业
	scale					规模
	nature					性质
	position				职位
	salary					薪资
	location				地址
	website					网址
	tag						标签
	
	server_ip				服务器ip地址(日志最后一个字段，仅用于内部调使用)
	
	
	/**************************工作需求日志**************************/
	time_stamp				日志时间戳
	log_type				日志类型
	search_key				日志唯一标识[0-9A-Za-z]{32}
	time_ms					处理耗时(ms)
	error_code				错误码
	
	key						标识
	src						来源
	name					职位
	company					公司
	industry				行业
	scale					规模
	nature					性质
	website					网址
	count					数量
	type					类型
	pubtime					发布
	offtime					截止
	salary					薪资
	location				地址
	major					学历
	school					学校
	experience				经验
	tag						标签
	duty					职责
	
	server_ip				服务器ip地址(日志最后一个字段，仅用于内部调使用)