INSERT INTO product (description, product_status) VALUES ('test desc 1', 'NEW');
INSERT INTO order_line (quantity_ordered, order_header_id, product_id) VALUES (10, 1, (select id from product where description like 'test desc 1'));
INSERT INTO category (description) VALUES ('category desc 1');
INSERT INTO product_category (product_id, category_id) VALUES (
                                                                  (select id from product where description like 'test desc 1'),
                                                                  (select id from category where description like 'category desc 1'));