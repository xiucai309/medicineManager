
USE medicineManage

--��ѯ����ҩƷĳÿһ��ȵ��������
SELECT medicine.medId as 'ҩƷID', 
medicine.medName as 'ҩƷ����', 
medicine.approvalNumber as 'ҩƷ��׼�ĺ�', 
medicine.introduce as 'ҩƷ����', 
medicine.isOTC as '�Ƿ�ΪOTC��ҩ', 
temp.profite as '�������' 
FROM medicine LEFT JOIN 
	(SELECT
		 medicine.medId as 'medId', 
				 sum((sellRecord.sellPrice - storageRecord.price) * sellRecord.sellNumber) as 'profite' 
					 FROM medicine, storageRecord, sellRecord 
					 WHERE medicine.medId = storageRecord.medId AND 
					 storageRecord.storageId = sellRecord.storageId AND 
					 convert(CHAR(10),sellRecord.sellDate, 120) LIKE '2017%'
					 GROUP BY medicine.medId) as temp  
		ON 
					 medicine.medId = temp.medId  


--��ѯ����ҩƷ�Ŀ���������
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

--��ѯĳһʱ��������ҩƷ������¼
SELECT storageRecord.storageId as '�洢ID',
					medicine.medName as 'ҩƷ����',
					storageRecord.productDate as 'ҩƷ��������',
					storageRecord.price as 'ҩƷ���۸�',
					storageRecord.storageNumber as 'ҩƷ�������',
					storageRecord.leastNumber as '����ҩƷʣ������',
					storageRecord.storageDate as 'ҩƷ���ʱ��',
					storageRecord.note as '��ע��Ϣ'
					FROM storageRecord, medicine WHERE
					storageRecord.medId = medicine.medId AND
					medicine.medName LIKE '%' AND
					convert(char(10),storageDate, 120) LIKE '2017%'

--��ѯ����ҩƷĳһ���ڵ����ۼ�¼
SELECT sellRecord.sellId as '����ID',
					medicine.medName as 'ҩƷ����',
					storageRecord.productDate as 'ҩƷ��������',
					sellRecord.sellPrice as 'ҩƷ���ۼ۸�',
					sellRecord.sellNumber as 'ҩƷ��������',
					sellRecord.sellDate as 'ҩƷ����ʱ��'
					FROM sellRecord, medicine, storageRecord WHERE 
					storageRecord.medId = medicine.medId AND 
					storageRecord.storageId = sellRecord.storageId AND 
					medicine.medName LIKE '%' AND 
					CONVERT(CHAR(10),sellDate, 120) LIKE '2017%'
                 

