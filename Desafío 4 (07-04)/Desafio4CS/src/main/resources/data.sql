DO $$
BEGIN
FOR i IN 1..100 LOOP
        INSERT INTO orden (cliente, fecha, total)
        VALUES (
            'Cliente_' || i,
            CURRENT_DATE - (i % 30),
            ROUND((random() * 10000 + 1000)::numeric, 2)
        );
END LOOP;
END
$$;
