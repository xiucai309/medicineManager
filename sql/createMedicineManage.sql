USE master
IF EXISTS (
	SELECT
		*
	FROM
		sysdatabases
	WHERE
		name = 'medicineManage'
) DROP DATABASE medicineManage

CREATE DATABASE medicineManage

go



USE medicineManage
go



/****************药品信息表********************/
CREATE TABLE medicine (
	medId INT IDENTITY NOT NULL,			     --药品编号                
	medName VARCHAR(25) NOT NULL,				 --药品名及生产商 unique 
	ShelfLife INT CHECK(ShelfLife > 0) NOT NULL, --药品保质期(天)
	isOTC BIT NOT NULL,                          --是否为处方药
	approvalNumber VARCHAR(25) NOT NULL,         --药品批准文号(国药准字 + 1位字母 + 8位数字)
	introduce VARCHAR(500) NOT NULL,             --药品介绍
	storageWay VARCHAR(50),                      --药品存储方式
	price FLOAT CHECK(price > 0)                 --药品售价
)

ALTER TABLE medicine 
	ADD CONSTRAINT PK_medId PRIMARY KEY (medId)

ALTER TABLE medicine
	ADD CONSTRAINT DF_isOTC DEFAULT (1) FOR isOTC

ALTER TABLE medicine
	ADD CONSTRAINT UQ_medName UNIQUE (medName)

ALTER TABLE medicine
	ADD CONSTRAINT UQ_approvalNumber UNIQUE (approvalNumber)


/*******************药品入库记录表**********************/
CREATE TABLE storageRecord (
	storageId INT IDENTITY NOT NULL,                           --入库编号
	medId INT NOT NULL,                                        --入库药品Id
	price FLOAT CHECK(price > 0) NOT NULL,                     --入库药品单价
	storageNumber INT CHECK (storageNumber > 0) NOT NULL,      --入库药品数量
	productDate datetime NOT NULL,                             --入库药品生产日期
	storageDate datetime NOT NULL,                             --入库日期(入库日期必须大于生产日期)
	leastNumber INT CHECK (leastNumber >= 0) NOT NULL,         --该批药品剩余量
	note VARCHAR(127)                                          --备注 
)

ALTER TABLE storageRecord 
	ADD CONSTRAINT PK_storageId PRIMARY KEY (storageId)

ALTER TABLE storageRecord
	ADD CONSTRAINT DF_storageDate DEFAULT (getDate()) FOR storageDate

ALTER TABLE storageRecord
	ADD CONSTRAINT FK_medId FOREIGN KEY (medId) REFERENCES medicine(medId)


/******************药品出售记录表****************/
CREATE TABLE sellRecord (
	sellId INT IDENTITY NOT NULL,                   --销售记录编号
	storageId INT NOT NULL,                         --所销售的药品入库编号
	sellNumber INT CHECK (sellNumber > 0) NOT NULL, --销售数量(销售数量必须小于等于该批药品的剩余数量)
	sellPrice FLOAT CHECK(sellPrice > 0) NOT NULL,  --销售单价
	sellDate datetime NOT NULL                      --销售日期
)

--alter table   
	--add constraint PK_sellId primary key (sellId)

ALTER TABLE sellRecord
	ADD CONSTRAINT FK_storageId FOREIGN KEY (storageId) REFERENCES storageRecord(storageId)

ALTER TABLE sellRecord
	ADD CONSTRAINT DF_sellDate DEFAULT (getDate()) FOR sellDate

go




--insert into medicine values('甘草锌颗粒', '180', 0, '国药准字H00000001', '治疗儿童锌缺乏症', '低温存储', 100)
INSERT INTO medicine VALUES('六味地黄丸', '180', 0, '国药准字H00000002', '治疗感冒', '低温存储', 50)
INSERT INTO medicine VALUES('维生素b6', '360', 0, '国药准字H00110003', '治疗感冒', '低温存储', 20)
INSERT INTO medicine VALUES('板蓝根冲剂', '90', 1, '国药准字H00001002', '治疗感冒', '常温存储', 30.5)
INSERT INTO medicine VALUES('葵花胃康灵', '120', 1, '国药准字H00010103', '治疗胃痛', '低温存储', 135)
INSERT INTO medicine VALUES('一清胶囊', '360', 0, '国药准字H19942013', '清热解毒', '低温存储', 26.5)
INSERT INTO medicine VALUES('甘草锌颗粒', '180', 0, '国药准字H00000001', '治疗儿童锌缺乏症', '低温存储', 100)

INSERT INTO storageRecord VALUES(1, 20, 500, '2011-09-11', '2013-09-11', 500, NULL)
INSERT INTO storageRecord VALUES(2, 20, 500, '2012-09-11', '2013-09-13', 500, NULL)
INSERT INTO storageRecord VALUES(3, 20, 500, '2012-10-11', '2013-11-09', 500, NULL)
INSERT INTO storageRecord VALUES(2, 20, 200, '2012-11-07', '2013-11-29', 200, NULL)
INSERT INTO storageRecord VALUES(6, 20, 100, '2012-12-28', '2014-12-11', 100, NULL)
INSERT INTO storageRecord VALUES(2, 10, 50, '2014-11-11', '2014-12-10', 50, NULL)
INSERT INTO storageRecord VALUES(1, 10.5, 300, '2015-4-07', '2015-8-8', 300, NULL)
INSERT INTO storageRecord VALUES(5, 30.5, 110, '2015-9-12', '2015-9-20', 110, NULL)
INSERT INTO storageRecord VALUES(3, 10, 11, '2015-11-1', '2015-11-10', 11, NULL)
INSERT INTO storageRecord VALUES(1, 10, 300, '2016-4-07', '2016-8-8', 300, NULL)
INSERT INTO storageRecord VALUES(6, 30, 110, '2016-9-12', '2016-9-20', 110, NULL)
INSERT INTO storageRecord VALUES(3, 10, 151, '2016-11-1', '2016-11-10', 151, NULL)

INSERT INTO storageRecord VALUES(2, 15, 12, '2016-9-13', '2016-11-15', 12, NULL)
INSERT INTO storageRecord VALUES(4, 17, 18, '2016-11-18', '2016-11-25', 18, NULL)
INSERT INTO storageRecord VALUES(1, 10.6, 150, '2016-11-07', '2016-12-1', 150, NULL)

go



--创建视图用于
--显示每种药品当前可出售的数量及价格信息(未过期药品)
--(药品编号，药品名，药品售价，药品可售数量，药品保质期)
CREATE VIEW sellTable
	AS
	SELECT medicine.medId as 'medId', medicine.medName as 'medName', medicine.approvalNumber as 'approvalNumber',
		   medicine.isOTC as 'isOTC', medicine.introduce as 'introduce', temp.leastNumber as 'leastNumber',
		   medicine.price as 'price'	
		 FROM medicine LEFT JOIN 
		(SELECT medicine.medId AS 'medId', SUM(storageRecord.leastNumber) AS 'leastNumber'

			FROM  
				medicine, storageRecord
				--storageRecord.medId = medicine.medId 
			WHERE 
				  storageRecord.medId = medicine.medId 
				AND
				  DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife
			GROUP BY 
				medicine.medID ) as temp
	ON 
        medicine.medId = temp.medId

go



--创建视图用于
--显示过期药品信息
--(入库编号，药品名，生产日期， 入库日期， 入库价格，过期药品数量， 过期时间[天])
CREATE VIEW overdueRecord
	AS
	
	SELECT storageRecord.storageId AS '存储ID', 
           medicine.medName AS '药品名称', 
           storageRecord.productDate AS '药品生产日期',
		   medicine.ShelfLife AS '药品保质期(天)',
           storageRecord.storageDate AS '药品入库日期', 
           storageRecord.price AS '药品入库单价',
		   storageRecord.leastNumber AS '该批药品剩余数',
		   (datediff(DAY, storageRecord.productDate, getDate())- medicine.ShelfLife) AS '药品超期时间(天)'
           	 
           FROM

		  (SELECT storageRecord.storageId AS 'storageId', 
			   MAX(medicine.medId) AS 'medID'
			FROM storageRecord, medicine 
			WHERE 
				  storageRecord.medId = medicine.medId 
				AND
				  datediff(DAY, storageRecord.productDate, getDate()) >= medicine.ShelfLife
			GROUP BY 
				storageRecord.storageId) AS temp, medicine, storageRecord
	WHERE
        temp.storageId = storageRecord.storageId
        AND
        temp.medId = medicine.medId
go


	

/************创建角色表**************/
CREATE TABLE role (
	roleId VARCHAR(8) NOT NULL,
    roleName VARCHAR(20) NOT NULL,
    roleMemo VARCHAR(30) NOT NULL
)

ALTER TABLE role 
	ADD CONSTRAINT PK_roleId PRIMARY KEY (roleId)

INSERT INTO role VALUES('admin','管理员', '管理员')
INSERT INTO role VALUES('sale','雇员', '雇员')


/************创建用户表**************/
CREATE TABLE misUser(
	[userId] [varchar](8) NOT NULL,
	[userName] [varchar](20) NULL,
	[userPwd] [varchar](20) NULL,
	[userMemo] [text] NULL,
	[roleId] [varchar](8) NULL
)

ALTER TABLE misUser 
	ADD CONSTRAINT PK_userId PRIMARY KEY (userId)

ALTER TABLE misUser
	ADD CONSTRAINT FK_roleId FOREIGN KEY (roleId) REFERENCES role(roleId)

INSERT INTO misUser VALUES('admin','admin', '123','admin','admin')


/***********创建菜单表**********/
CREATE TABLE menu(
	menuId VARCHAR(8) NOT NULL,
	menuName VARCHAR(20) NOT NULL,
	menuMemo TEXT NOT NULL,
	functionClass VARCHAR(127) 
)
ALTER TABLE menu 
	ADD CONSTRAINT PK_menuId PRIMARY KEY (menuId)
--alter table menu
	--add constraint FK_mroleId foreign key (roleId) references role(roleId)

INSERT INTO menu VALUES('10', '库存管理', '药品进库、仓库清理', NULL);

INSERT INTO menu
VALUES
	(
		'1010',
		'药品入库',
		'添加新药品入库',
		'com.medicine.control.action.org.StorageMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'1020',
		'入库记录查询',
		'入库记录查询',
		'com.medicine.control.action.org.SearchStorageRecordAction'
	) INSERT INTO menu
VALUES
	(
		'1030',
		'过期药品查询',
		'清理已经过期的药品',
		'com.medicine.control.action.org.SearchOverdueRecord'
	) INSERT INTO menu
VALUES
	(
		'20',
		'药品信息',
		'药品信息注册、药品信息修改',
		NULL
	) INSERT INTO menu
VALUES
	(
		'2010',
		'药品信息注册',
		'药品信息注册',
		'com.medicine.control.action.org.AddMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'2020',
		'药品信息管理',
		'药品信息查询、修改',
		'com.medicine.control.action.org.SearchMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'30',
		'药品销售',
		'药品销售',
		NULL
	) INSERT INTO menu
VALUES
	(
		'3010',
		'药品销售',
		'药品销售',
		'com.medicine.control.action.org.SellMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'3020',
		'销售记录查询',
		'销售记录查询',
		'com.medicine.control.action.org.SearchSellRecord'
	) INSERT INTO menu
VALUES
	(
		'40',
		'利润查询',
		'利润查询',
		NULL
	) INSERT INTO menu
VALUES
	(
		'4010',
		'利润查询',
		'利润查询',
		'com.medicine.control.action.org.SearchProfiteAction'
	) 
--SELECT * FROM storageRecord WHERE 1=1  AND storageDate LIKE '%2017%'
--SELECT convert(char(10),storageDate, 120) FROM storageRecord WHERE convert(char(10),storageDate) LIKE '%2017-01%'
--SELECT convert(char(10),storageDate, 100) FROM storageRecord WHERE 1=1
	/*
create proc[edure] procName 
	参数列表 --输入参数按值传递
			 --输出参数按引用传递
	@参数名 数据类型 = 默认值[output] 
as 
	--- T-SQL编程,定义变量，流程控制等
Go 

exec[ute] procName 参数
*/

go
--销售处理事务
CREATE PROCEDURE sell_procedure
	@medId INT,
	@sellNumber INT 
AS
	DECLARE @errorcount INT
	SET @errorcount = 0
	DECLARE @leastNumber INT
	DECLARE @sellPrice FLOAT 
	DECLARE @storageId INT
	DECLARE @minProDate VARCHAR(50)
	
	BEGIN TRANSACTION
		--查找是否有指定的药品且未过保质期且剩余数量大于0
		WHILE EXISTS (SELECT * FROM storageRecord, medicine
							   WHERE storageRecord.medId = medicine.medId AND
									 storageRecord.medId = @medId AND                                               --指定药品编号
									 storageRecord.leastNumber > 0 AND                                              --药品有剩余
									 DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife)     --药品未过期
		BEGIN
			--查找符合要求的批次中生产日期最早的药品日期
			SELECT @minProDate = MIN(storageRecord.productDate) 
							   FROM storageRecord, medicine
							   WHERE storageRecord.medId = medicine.medId AND
									 storageRecord.medId = @medId AND                                               --指定药品编号
									 storageRecord.leastNumber > 0 AND                                              --药品有剩余
									 DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife      --药品未过期    
							
			--获取该批药品剩余数量和药品入库编号
			SELECT @leastNumber = storageRecord.leastNumber, @storageId = storageRecord.storageId 
							   FROM storageRecord, medicine
							   WHERE storageRecord.medId = medicine.medId AND
									 storageRecord.medId = @medId AND                                               --指定药品编号
									 storageRecord.leastNumber > 0 AND                                              --药品有剩余
									 DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife AND  --药品未过期    
							         storageRecord.productDate = @minProDate                                        --选择生产日期最早的药品
			--查找药品售价
			SELECT @sellPrice = price FROM medicine WHERE medId = @medId

			IF(@leastNumber >= @sellNumber)
				BEGIN
					--将选择的该批药品剩余数量减少@sellNumber
					UPDATE storageRecord SET leastNumber = leastNumber - @sellNumber
						WHERE storageId = @storageId
					SET @errorcount = @errorcount + @@error
					
					--向销售记录中加入一条当前的销售记录
					INSERT INTO sellRecord VALUES(@storageId, @sellNumber, @sellPrice, getDate())
					SET @sellNumber = 0
					SET @errorcount = @errorcount + @@error
					
					BREAK
				END
			ELSE
				BEGIN
					--售出该批次的所有药品
					UPDATE storageRecord SET leastNumber = 0
						WHERE storageId = @storageId
					SET @errorcount = @errorcount + @@error
					
					--向销售记录中加入一条当前的销售记录
					INSERT INTO sellRecord VALUES(@storageId, @leastNumber, @sellPrice, getDate())
					SET @errorcount = @errorcount + @@error
					SET @sellNumber = @sellNumber - @leastNumber
				END
		END
		
		IF(@errorcount <> 0 OR @sellNumber > 0)
			ROLLBACK TRANSACTION
			
	COMMIT TRANSACTION
go



--sell_procedure测试
execute sell_procedure @medId=2, @sellNumber = 1

/*
--创建触发器，当入库记录中的药品剩余数量减少时，自动向销售表中添加销售记录
CREATE TRIGGER sell_trigger
ON storageRecord
FOR UPDATE

AS
	DECLARE @oldNumber INT
	DECLARE @newNumber INT
	DECLARE @storageId INT
	DECLARE @sellNumber INT
	DECLARE @medId INT
	DECLARE @sellPrice FLOAT
	
	SELECT @newNumber = leastNumber FROM inserted
	SELECT @oldNumber = leastNumber, @storageId = storageId FROM deleted

	IF(@oldNumber > @newNumber)
	BEGIN
		SELECT @medId = medId FROM storageRecord WHERE storageId = @storageId
		SELECT @sellPrice = price FROM medicine WHERE medId = @medId
		SET @sellNumber = @oldNumber - @newNumber
		
	    --向销售记录中添加新的销售记录
		INSERT INTO sellRecord VALUES(@storageId, @sellNumber, @sellPrice, getDate())
	END
go



--触发器测试
UPDATE storageRecord SET leastNumber = leastNumber - 5 WHERE storageId = 9
*/
--select datediff(day, '2016-4-7', getDate()) from storageRecord

--药品年度利润查询
SELECT * FROM medicine LEFT JOIN
	(SELECT 
			medicine.medId as 'medId', 
			sum((sellRecord.sellPrice - storageRecord.price) * sellRecord.sellNumber) as 'profite' 
		 FROM medicine, storageRecord, sellRecord 
		 WHERE medicine.medId = storageRecord.medId AND 
			   storageRecord.storageId = sellRecord.storageId AND
			   medicine.medName LIKE '%' AND 
			   convert(CHAR(10),sellRecord.sellDate, 120) LIKE '2017%' 
		 GROUP BY medicine.medId) as temp
     ON
	     medicine.medId = temp.medId
