
USE medicineManage

--查询所有药品某每一年度的年度利润
SELECT medicine.medId as '药品ID', 
medicine.medName as '药品名称', 
medicine.approvalNumber as '药品批准文号', 
medicine.introduce as '药品介绍', 
medicine.isOTC as '是否为OTC用药', 
temp.profite as '年度利润' 
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


--查询所有药品的可销售数量
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

--查询某一时间内所有药品的入库记录
SELECT storageRecord.storageId as '存储ID',
					medicine.medName as '药品名称',
					storageRecord.productDate as '药品生产日期',
					storageRecord.price as '药品入库价格',
					storageRecord.storageNumber as '药品入库数量',
					storageRecord.leastNumber as '该批药品剩余数量',
					storageRecord.storageDate as '药品入库时间',
					storageRecord.note as '备注信息'
					FROM storageRecord, medicine WHERE
					storageRecord.medId = medicine.medId AND
					medicine.medName LIKE '%' AND
					convert(char(10),storageDate, 120) LIKE '2017%'

--查询所有药品某一年内的销售记录
SELECT sellRecord.sellId as '销售ID',
					medicine.medName as '药品名称',
					storageRecord.productDate as '药品生产日期',
					sellRecord.sellPrice as '药品销售价格',
					sellRecord.sellNumber as '药品销售数量',
					sellRecord.sellDate as '药品销售时间'
					FROM sellRecord, medicine, storageRecord WHERE 
					storageRecord.medId = medicine.medId AND 
					storageRecord.storageId = sellRecord.storageId AND 
					medicine.medName LIKE '%' AND 
					CONVERT(CHAR(10),sellDate, 120) LIKE '2017%'
                 

