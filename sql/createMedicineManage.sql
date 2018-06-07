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



/****************ҩƷ��Ϣ��********************/
CREATE TABLE medicine (
	medId INT IDENTITY NOT NULL,			     --ҩƷ���                
	medName VARCHAR(25) NOT NULL,				 --ҩƷ���������� unique 
	ShelfLife INT CHECK(ShelfLife > 0) NOT NULL, --ҩƷ������(��)
	isOTC BIT NOT NULL,                          --�Ƿ�Ϊ����ҩ
	approvalNumber VARCHAR(25) NOT NULL,         --ҩƷ��׼�ĺ�(��ҩ׼�� + 1λ��ĸ + 8λ����)
	introduce VARCHAR(500) NOT NULL,             --ҩƷ����
	storageWay VARCHAR(50),                      --ҩƷ�洢��ʽ
	price FLOAT CHECK(price > 0)                 --ҩƷ�ۼ�
)

ALTER TABLE medicine 
	ADD CONSTRAINT PK_medId PRIMARY KEY (medId)

ALTER TABLE medicine
	ADD CONSTRAINT DF_isOTC DEFAULT (1) FOR isOTC

ALTER TABLE medicine
	ADD CONSTRAINT UQ_medName UNIQUE (medName)

ALTER TABLE medicine
	ADD CONSTRAINT UQ_approvalNumber UNIQUE (approvalNumber)


/*******************ҩƷ����¼��**********************/
CREATE TABLE storageRecord (
	storageId INT IDENTITY NOT NULL,                           --�����
	medId INT NOT NULL,                                        --���ҩƷId
	price FLOAT CHECK(price > 0) NOT NULL,                     --���ҩƷ����
	storageNumber INT CHECK (storageNumber > 0) NOT NULL,      --���ҩƷ����
	productDate datetime NOT NULL,                             --���ҩƷ��������
	storageDate datetime NOT NULL,                             --�������(������ڱ��������������)
	leastNumber INT CHECK (leastNumber >= 0) NOT NULL,         --����ҩƷʣ����
	note VARCHAR(127)                                          --��ע 
)

ALTER TABLE storageRecord 
	ADD CONSTRAINT PK_storageId PRIMARY KEY (storageId)

ALTER TABLE storageRecord
	ADD CONSTRAINT DF_storageDate DEFAULT (getDate()) FOR storageDate

ALTER TABLE storageRecord
	ADD CONSTRAINT FK_medId FOREIGN KEY (medId) REFERENCES medicine(medId)


/******************ҩƷ���ۼ�¼��****************/
CREATE TABLE sellRecord (
	sellId INT IDENTITY NOT NULL,                   --���ۼ�¼���
	storageId INT NOT NULL,                         --�����۵�ҩƷ�����
	sellNumber INT CHECK (sellNumber > 0) NOT NULL, --��������(������������С�ڵ��ڸ���ҩƷ��ʣ������)
	sellPrice FLOAT CHECK(sellPrice > 0) NOT NULL,  --���۵���
	sellDate datetime NOT NULL                      --��������
)

--alter table   
	--add constraint PK_sellId primary key (sellId)

ALTER TABLE sellRecord
	ADD CONSTRAINT FK_storageId FOREIGN KEY (storageId) REFERENCES storageRecord(storageId)

ALTER TABLE sellRecord
	ADD CONSTRAINT DF_sellDate DEFAULT (getDate()) FOR sellDate

go




--insert into medicine values('�ʲ�п����', '180', 0, '��ҩ׼��H00000001', '���ƶ�ͯпȱ��֢', '���´洢', 100)
INSERT INTO medicine VALUES('��ζ�ػ���', '180', 0, '��ҩ׼��H00000002', '���Ƹ�ð', '���´洢', 50)
INSERT INTO medicine VALUES('ά����b6', '360', 0, '��ҩ׼��H00110003', '���Ƹ�ð', '���´洢', 20)
INSERT INTO medicine VALUES('���������', '90', 1, '��ҩ׼��H00001002', '���Ƹ�ð', '���´洢', 30.5)
INSERT INTO medicine VALUES('����θ����', '120', 1, '��ҩ׼��H00010103', '����θʹ', '���´洢', 135)
INSERT INTO medicine VALUES('һ�彺��', '360', 0, '��ҩ׼��H19942013', '���Ƚⶾ', '���´洢', 26.5)
INSERT INTO medicine VALUES('�ʲ�п����', '180', 0, '��ҩ׼��H00000001', '���ƶ�ͯпȱ��֢', '���´洢', 100)

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



--������ͼ����
--��ʾÿ��ҩƷ��ǰ�ɳ��۵��������۸���Ϣ(δ����ҩƷ)
--(ҩƷ��ţ�ҩƷ����ҩƷ�ۼۣ�ҩƷ����������ҩƷ������)
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



--������ͼ����
--��ʾ����ҩƷ��Ϣ
--(����ţ�ҩƷ�����������ڣ� ������ڣ� ���۸񣬹���ҩƷ������ ����ʱ��[��])
CREATE VIEW overdueRecord
	AS
	
	SELECT storageRecord.storageId AS '�洢ID', 
           medicine.medName AS 'ҩƷ����', 
           storageRecord.productDate AS 'ҩƷ��������',
		   medicine.ShelfLife AS 'ҩƷ������(��)',
           storageRecord.storageDate AS 'ҩƷ�������', 
           storageRecord.price AS 'ҩƷ��ⵥ��',
		   storageRecord.leastNumber AS '����ҩƷʣ����',
		   (datediff(DAY, storageRecord.productDate, getDate())- medicine.ShelfLife) AS 'ҩƷ����ʱ��(��)'
           	 
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


	

/************������ɫ��**************/
CREATE TABLE role (
	roleId VARCHAR(8) NOT NULL,
    roleName VARCHAR(20) NOT NULL,
    roleMemo VARCHAR(30) NOT NULL
)

ALTER TABLE role 
	ADD CONSTRAINT PK_roleId PRIMARY KEY (roleId)

INSERT INTO role VALUES('admin','����Ա', '����Ա')
INSERT INTO role VALUES('sale','��Ա', '��Ա')


/************�����û���**************/
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


/***********�����˵���**********/
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

INSERT INTO menu VALUES('10', '������', 'ҩƷ���⡢�ֿ�����', NULL);

INSERT INTO menu
VALUES
	(
		'1010',
		'ҩƷ���',
		'�����ҩƷ���',
		'com.medicine.control.action.org.StorageMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'1020',
		'����¼��ѯ',
		'����¼��ѯ',
		'com.medicine.control.action.org.SearchStorageRecordAction'
	) INSERT INTO menu
VALUES
	(
		'1030',
		'����ҩƷ��ѯ',
		'�����Ѿ����ڵ�ҩƷ',
		'com.medicine.control.action.org.SearchOverdueRecord'
	) INSERT INTO menu
VALUES
	(
		'20',
		'ҩƷ��Ϣ',
		'ҩƷ��Ϣע�ᡢҩƷ��Ϣ�޸�',
		NULL
	) INSERT INTO menu
VALUES
	(
		'2010',
		'ҩƷ��Ϣע��',
		'ҩƷ��Ϣע��',
		'com.medicine.control.action.org.AddMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'2020',
		'ҩƷ��Ϣ����',
		'ҩƷ��Ϣ��ѯ���޸�',
		'com.medicine.control.action.org.SearchMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'30',
		'ҩƷ����',
		'ҩƷ����',
		NULL
	) INSERT INTO menu
VALUES
	(
		'3010',
		'ҩƷ����',
		'ҩƷ����',
		'com.medicine.control.action.org.SellMedicineAction'
	) INSERT INTO menu
VALUES
	(
		'3020',
		'���ۼ�¼��ѯ',
		'���ۼ�¼��ѯ',
		'com.medicine.control.action.org.SearchSellRecord'
	) INSERT INTO menu
VALUES
	(
		'40',
		'�����ѯ',
		'�����ѯ',
		NULL
	) INSERT INTO menu
VALUES
	(
		'4010',
		'�����ѯ',
		'�����ѯ',
		'com.medicine.control.action.org.SearchProfiteAction'
	) 
--SELECT * FROM storageRecord WHERE 1=1  AND storageDate LIKE '%2017%'
--SELECT convert(char(10),storageDate, 120) FROM storageRecord WHERE convert(char(10),storageDate) LIKE '%2017-01%'
--SELECT convert(char(10),storageDate, 100) FROM storageRecord WHERE 1=1
	/*
create proc[edure] procName 
	�����б� --���������ֵ����
			 --������������ô���
	@������ �������� = Ĭ��ֵ[output] 
as 
	--- T-SQL���,������������̿��Ƶ�
Go 

exec[ute] procName ����
*/

go
--���۴�������
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
		--�����Ƿ���ָ����ҩƷ��δ����������ʣ����������0
		WHILE EXISTS (SELECT * FROM storageRecord, medicine
							   WHERE storageRecord.medId = medicine.medId AND
									 storageRecord.medId = @medId AND                                               --ָ��ҩƷ���
									 storageRecord.leastNumber > 0 AND                                              --ҩƷ��ʣ��
									 DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife)     --ҩƷδ����
		BEGIN
			--���ҷ���Ҫ����������������������ҩƷ����
			SELECT @minProDate = MIN(storageRecord.productDate) 
							   FROM storageRecord, medicine
							   WHERE storageRecord.medId = medicine.medId AND
									 storageRecord.medId = @medId AND                                               --ָ��ҩƷ���
									 storageRecord.leastNumber > 0 AND                                              --ҩƷ��ʣ��
									 DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife      --ҩƷδ����    
							
			--��ȡ����ҩƷʣ��������ҩƷ�����
			SELECT @leastNumber = storageRecord.leastNumber, @storageId = storageRecord.storageId 
							   FROM storageRecord, medicine
							   WHERE storageRecord.medId = medicine.medId AND
									 storageRecord.medId = @medId AND                                               --ָ��ҩƷ���
									 storageRecord.leastNumber > 0 AND                                              --ҩƷ��ʣ��
									 DATEDIFF(DAY, storageRecord.productDate, getDate()) < medicine.ShelfLife AND  --ҩƷδ����    
							         storageRecord.productDate = @minProDate                                        --ѡ���������������ҩƷ
			--����ҩƷ�ۼ�
			SELECT @sellPrice = price FROM medicine WHERE medId = @medId

			IF(@leastNumber >= @sellNumber)
				BEGIN
					--��ѡ��ĸ���ҩƷʣ����������@sellNumber
					UPDATE storageRecord SET leastNumber = leastNumber - @sellNumber
						WHERE storageId = @storageId
					SET @errorcount = @errorcount + @@error
					
					--�����ۼ�¼�м���һ����ǰ�����ۼ�¼
					INSERT INTO sellRecord VALUES(@storageId, @sellNumber, @sellPrice, getDate())
					SET @sellNumber = 0
					SET @errorcount = @errorcount + @@error
					
					BREAK
				END
			ELSE
				BEGIN
					--�۳������ε�����ҩƷ
					UPDATE storageRecord SET leastNumber = 0
						WHERE storageId = @storageId
					SET @errorcount = @errorcount + @@error
					
					--�����ۼ�¼�м���һ����ǰ�����ۼ�¼
					INSERT INTO sellRecord VALUES(@storageId, @leastNumber, @sellPrice, getDate())
					SET @errorcount = @errorcount + @@error
					SET @sellNumber = @sellNumber - @leastNumber
				END
		END
		
		IF(@errorcount <> 0 OR @sellNumber > 0)
			ROLLBACK TRANSACTION
			
	COMMIT TRANSACTION
go



--sell_procedure����
execute sell_procedure @medId=2, @sellNumber = 1

/*
--������������������¼�е�ҩƷʣ����������ʱ���Զ������۱���������ۼ�¼
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
		
	    --�����ۼ�¼������µ����ۼ�¼
		INSERT INTO sellRecord VALUES(@storageId, @sellNumber, @sellPrice, getDate())
	END
go



--����������
UPDATE storageRecord SET leastNumber = leastNumber - 5 WHERE storageId = 9
*/
--select datediff(day, '2016-4-7', getDate()) from storageRecord

--ҩƷ��������ѯ
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
