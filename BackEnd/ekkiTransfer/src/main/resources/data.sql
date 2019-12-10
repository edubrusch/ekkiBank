
INSERT INTO favoured(name,document,bank,acount_Number) values ('Favorecido Joao', '98654654', '001', '1354989');
INSERT INTO favoured(name,document,bank,acount_Number) values ('Favorecido Claber', '98654654', '001', '1354989');
INSERT INTO favoured(name,document,bank,acount_Number) values ('Favorecida Renata', '98654654', '001', '1354989');
INSERT INTO favoured(name,document,bank,acount_Number) values ('Favorecida Silvia', '98654654', '001', '1354989');

INSERT INTO credit_card(serial,titular_name,CVV,due_Date,acount_Number) values ('89778965498789456465-15143', 'Vera L. B. S.', 347, '07/25', '878587');
INSERT INTO credit_card(serial,titular_name,CVV,due_Date,acount_Number) values ('77665365451565656664-35354', 'Jonas R. K.', 654, '07/25', '123432');
INSERT INTO credit_card(serial,titular_name,CVV,due_Date,acount_Number) values ('23426365546213154353-62345', 'Joquim Cust B. C.', 223, '09/20', '324232');
INSERT INTO credit_card(serial,titular_name,CVV,due_Date,acount_Number) values ('08986875865565435443-62354', 'Martha R H N', 947, '08/21', '454336');

INSERT INTO account(name, account_Number, balance, credit, has_Credit) values ('Joana Machado Prado', 526546, 500.50, 0, false);
INSERT INTO account(name, account_Number, balance, credit, has_Credit) values ('Neusa Soares Silva', 524756, 500.50, 5000.00, true);	

COMMIT;



	