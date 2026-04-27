-- Manual seed data for the current product model.
-- Tables:
-- manufacturers(manufacturer_name)
-- products(product_name, product_description, product_price, manufacturer_id)
-- product_images(product_id, image_url, is_main)

INSERT INTO manufacturers (manufacturer_name) VALUES
('Trek'),
('Specialized'),
('Giant'),
('Merida'),
('Scott'),
('Shimano'),
('Continental'),
('Topeak'),
('Abus'),
('Lezyne');

INSERT INTO products (product_name, product_description, product_price, manufacturer_id) VALUES
('Горный велосипед Trek Marlin 7', 'Универсальный MTB для города, парков и легких трейлов. Алюминиевая рама и надежная трансмиссия.', 68990, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Trek')),
('Горный велосипед Specialized Rockhopper', 'Хардтейл начального среднего класса для уверенного старта в кросс-кантри и активных поездках.', 73490, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Specialized')),
('Шоссейный велосипед Giant Contend AR 2', 'Комфортная шоссейная модель для тренировок, фитнеса и длительных асфальтовых маршрутов.', 89990, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Giant')),
('Городской велосипед Merida Crossway 100', 'Практичный гибрид для города и парковых дорожек с удобной посадкой.', 52990, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Merida')),
('Детский велосипед Scott Roxter 20', 'Яркая и надежная модель для детей с безопасной геометрией и хорошим накатом.', 28990, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Scott')),
('Велошлем Abus Urban-I 3.0', 'Городской шлем с хорошей вентиляцией, регулировкой посадки и высокой заметностью.', 6490, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Abus')),
('Велошлем Abus MoDrop', 'Шлем для трейлового катания с увеличенной защитой затылочной зоны.', 7990, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Abus')),
('Передний фонарь Lezyne Hecto Drive 500XL', 'Компактный фонарь с USB-зарядкой и несколькими режимами свечения.', 4290, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Lezyne')),
('Задний фонарь Lezyne Strip Drive', 'Яркий задний фонарь для повышения заметности на дороге в темное время.', 2390, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Lezyne')),
('Напольный насос Topeak JoeBlow Sport', 'Удобный насос с манометром для точной накачки велосипедных колес.', 3590, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Topeak')),
('Мультитул Topeak Mini 9', 'Компактный набор инструментов для настройки велосипеда в дороге.', 1890, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Topeak')),
('Камера 29 x 2.10', 'Камера для горных велосипедов с автониппелем и повышенной износостойкостью.', 690, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Continental')),
('Покрышка Continental Race King 29 x 2.20', 'Быстрая покрышка для сухих трасс и универсального кросс-кантри.', 3290, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Continental')),
('Покрышка Continental Contact Urban 700x42', 'Городская покрышка с хорошим сцеплением и защитой от проколов.', 2890, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Continental')),
('Цепь Shimano CN-HG53 9-speed', 'Надежная цепь для 9-скоростной трансмиссии с плавным переключением.', 1990, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Shimano')),
('Кассета Shimano CS-HG400 11-32', 'Кассета для 9-скоростных систем с универсальным диапазоном передач.', 3790, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Shimano')),
('Задний переключатель Shimano Alivio RD-M3100', 'Переключатель для MTB-трансмиссий с точной работой и хорошим ресурсом.', 4590, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Shimano')),
('Седло Giant Connect Comfort', 'Комфортное седло для прогулочных и городских велосипедов.', 2490, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Giant')),
('Флягодержатель Specialized Zee Cage II', 'Легкий и прочный флягодержатель с надежной фиксацией бутылки.', 1590, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Specialized')),
('Велосумка Topeak Aero Wedge Pack', 'Подседельная сумка для камеры, мультитула и мелких аксессуаров.', 2790, (SELECT id FROM manufacturers WHERE manufacturer_name = 'Topeak'));

INSERT INTO product_images (product_id, image_url, is_main) VALUES
((SELECT id FROM products WHERE product_name = 'Горный велосипед Trek Marlin 7'), '/products/trek-marlin-7-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Горный велосипед Trek Marlin 7'), '/products/trek-marlin-7-side.jpg', false),
((SELECT id FROM products WHERE product_name = 'Горный велосипед Specialized Rockhopper'), '/products/specialized-rockhopper-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Шоссейный велосипед Giant Contend AR 2'), '/products/giant-contend-ar2-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Городской велосипед Merida Crossway 100'), '/products/merida-crossway-100-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Детский велосипед Scott Roxter 20'), '/products/scott-roxter-20-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Велошлем Abus Urban-I 3.0'), '/products/abus-urban-i-3-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Велошлем Abus MoDrop'), '/products/abus-modrop-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Передний фонарь Lezyne Hecto Drive 500XL'), '/products/lezyne-hecto-500xl-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Задний фонарь Lezyne Strip Drive'), '/products/lezyne-strip-drive-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Напольный насос Topeak JoeBlow Sport'), '/products/topeak-joeblow-sport-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Мультитул Topeak Mini 9'), '/products/topeak-mini-9-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Камера 29 x 2.10'), '/products/camera-29x210-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Покрышка Continental Race King 29 x 2.20'), '/products/continental-race-king-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Покрышка Continental Contact Urban 700x42'), '/products/continental-contact-urban-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Цепь Shimano CN-HG53 9-speed'), '/products/shimano-cnhg53-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Кассета Shimano CS-HG400 11-32'), '/products/shimano-cshg400-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Задний переключатель Shimano Alivio RD-M3100'), '/products/shimano-alivio-rdm3100-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Седло Giant Connect Comfort'), '/products/giant-connect-comfort-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Флягодержатель Specialized Zee Cage II'), '/products/specialized-zee-cage-2-main.jpg', true),
((SELECT id FROM products WHERE product_name = 'Велосумка Topeak Aero Wedge Pack'), '/products/topeak-aero-wedge-main.jpg', true);
