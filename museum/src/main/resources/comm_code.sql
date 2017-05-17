truncate table comm_code;
INSERT INTO comm_code(codeid, tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''), 'coll_info', 'isDeliver', '已移交', 1, 1);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'coll_info', 'isDeliver', '未移交', 0, 0);

INSERT INTO comm_code(codeid, tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''), 'coll_info', 'isAppraisal', '已鉴定', 1, 1);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'coll_info', 'isAppraisal', '未鉴定', 0, 0);

INSERT INTO comm_code(codeid, tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''), 'coll_info', 'status', '已征集', 1, 1);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'coll_info', 'status', '未征集', 0, 0);

INSERT INTO comm_code(codeid, tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''), 'share', 'currency', '美元', 1, 1);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'share', 'currency', '人民币', 0, 0);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'share', 'currency', '港元', 2, 0);

INSERT INTO comm_code(codeid, tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''), 'coll_clue', 'status', '联系中', 0, 1);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'coll_clue', 'status', '拒绝', 3, 2);
INSERT INTO comm_code(codeid, tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''), 'coll_clue', 'status', '符合入馆', 1, 3);
INSERT INTO comm_code(codeid,tablename, fieldname, CODENAME, CODE, ORDERBY) 
VALUES (replace(uuid(),'-',''),'coll_clue', 'status', '不符合入馆', 2, 4);
