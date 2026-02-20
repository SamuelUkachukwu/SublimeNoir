INSERT INTO
    users (id, username, email, first_name, last_name)
VALUES
    (
        1,
        'sean.oconnor',
        'sean.oconnor@example.com',
        'Seán',
        'O’Connor'
    ),
    (
        2,
        'chinedu.okafor',
        'chinedu.okafor@test.com',
        'Chinedu',
        'Okafor'
    ),
    (
        3,
        'aminat.bello',
        'aminat.bello@test.com',
        'Aminat',
        'Bello'
    );

INSERT INTO
    product (product_id, name, brand, price, sizeML, quantity)
VALUES
    (
        1,
        'Noir Essence',
        'Sublime Noir',
        129.99,
        100,
        30
    ),
    (
        2,
        'Sublime Bloom',
        'Sublime Noir',
        159.99,
        80,
        40
    ),
    (
        3,
        'Velvet Eclipse',
        'Sublime Noir',
        189.99,
        120,
        20
    );

INSERT INTO
    orders (order_id, order_date, status, user_id)
VALUES
    (1, CURRENT_DATE, 'PENDING', 1),
    (2, CURRENT_DATE, 'PENDING', 2),
    (3, CURRENT_DATE, 'SHIPPED', 3);

INSERT INTO
    order_item (
    id,
    order_id,
    product_id,
    quantity,
    price_at_purchase
)
VALUES
    -- order 1
    (1, 1, 1, 1, 129.99),
    -- order 2
    (2, 2, 1, 2, 129.99),
    (3, 2, 2, 1, 159.99),
    -- order 3
    (4, 3, 3, 1, 189.99),
    (5, 3, 2, 2, 159.99);