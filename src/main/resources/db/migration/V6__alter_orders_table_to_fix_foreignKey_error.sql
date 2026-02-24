ALTER TABLE orders
DROP FOREIGN KEY orders_users_id_fk;

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_customer
        FOREIGN KEY (customer_id) REFERENCES users(id);