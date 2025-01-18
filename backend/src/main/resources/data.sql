
-- 데이터 삽입
INSERT INTO admin (username, password)
VALUES
    ('user1', '1234');

INSERT INTO product (product_name, price, quantity, img_path, admin_id)
VALUES
    ('콜롬비아 원두', 5000, 3, '', 1),
    ('브라질 원두', 7000, 5, '', 1),
    ('아라비카 원두', 10000, 5, '', 1),
    ('케냐 원두', 7000, 5, '', 1);


INSERT INTO orders (email, address, postal_code, state, total_price)
VALUES
    ('asd123@naver.com', '서울시 강남구 청담동', '11111', 'PENDING', 43000),
    ('zxc789@naver.com', '대전광역시 서구 둔산동', '22222', 'PENDING', 25000);

INSERT INTO order_detail (ORDER_ID, PRODUCT_ID, QUANTITY)
VALUES
    (1, 1, 3),
    (1, 2, 4),
    (2, 1, 5);