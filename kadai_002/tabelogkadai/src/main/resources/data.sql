--categoriesテーブル
INSERT IGNORE INTO categories(id, category_name)VALUE(1, '寿司');
INSERT IGNORE INTO categories(id, category_name)VALUE(2, '天ぷら');
INSERT IGNORE INTO categories(id, category_name)VALUE(3, 'とんかつ');
INSERT IGNORE INTO categories(id, category_name)VALUE(4, '焼き鳥');
INSERT IGNORE INTO categories(id, category_name)VALUE(5, 'しゃぶしゃぶ');
INSERT IGNORE INTO categories(id, category_name)VALUE(6, '蕎麦');
INSERT IGNORE INTO categories(id, category_name)VALUE(7, 'うどん');
INSERT IGNORE INTO categories(id, category_name)VALUE(8, 'お好み焼き・たこ焼き');
INSERT IGNORE INTO categories(id, category_name)VALUE(9, '洋食');
INSERT IGNORE INTO categories(id, category_name)VALUE(10, 'イタリアン');
INSERT IGNORE INTO categories(id, category_name)VALUE(11, 'スペイン料理');

--typesテーブル
INSERT IGNORE INTO types(id, type_name_en, type_name_jp)VALUE(1, 'ROLE_FREEUSER', '無料会員');
INSERT IGNORE INTO types(id, type_name_en, type_name_jp)VALUE(2, 'ROLE_PAIDUSER', '有料会員');
INSERT IGNORE INTO types(id, type_name_en, type_name_jp)VALUE(3, 'ROLE_ADMINSHOP', '店舗管理者');
INSERT IGNORE INTO types(id, type_name_en, type_name_jp)VALUE(4, 'ROLE_ADMINAPPS', 'アプリ管理者');

--usersテーブル
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(1, '田中　太郎', 'タナカ　タロウ', '090-1234-5678', 'taro.tanaka@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(2, '田中　二郎', 'タナカ　ジロウ', '090-1234-6789', 'jiro.tanaka@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(3, '田中　三郎', 'タナカ　サブロウ', '090-1234-8765', 'saburo.tanaka@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(4, '田中　四郎', 'タナカ　シロウ', '090-1234-9876', 'shiro.tanaka@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 4, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(5, '佐藤　花子', 'サトウ　ハナコ', '090-4321-5678', 'hanako.sato@example.com', 'password', 2, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(6, '佐藤　幸子', 'サトウ　サチコ', '090-4321-6789', 'sachiko.sato@example.com', 'password', 1, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(7, '佐藤　春美', 'サトウ　ハルミ', '090-4321-8765', 'harumi.sato@example.com', 'password', 3, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(8, '佐藤　夏美', 'サトウ　ナツミ', '090-4321-9876', 'natsumi.sato@example.com', 'password', 2, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(9, '清水　明子', 'シミズ　アキコ', '080-1234-5678', 'akiko.shimizu@example.com', 'password', 2, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(10, '清水　雄太', 'シミズ　ユウタ', '080-1234-6789', 'yuta.shimizu@example.com', 'password', 3, true);
INSERT IGNORE INTO users(id, user_name, furigana, phone_number, email, password, user_type_id, enabled)VALUE(11, '清水　冬美', 'シミズ　フユミ', '080-1234-8765', 'fuyumi.shimizu@example.com', 'password', 3, true);

--shopsテーブル
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(1, 10, 'ブルーオーシャン', 'ブルーオーシャン', 'Blue Ocean', 'shop01.jpg', 'おしゃれなイタリアンカフェになっています。', '08:00', '20:00', '月曜日', 1000, 3000, '愛知県名古屋市港区X-XX-XX', '052-111-1111', 100);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(2, 11, 'レッドオーシャン', 'レッドオーシャン', 'Red Ocean', 'shop02.jpg', 'おすすめはパエリアです。', '08:00', '20:00', '不定休', 5000, 10000, '愛知県名古屋市北区X-XX-XX', '052-111-1112', 20);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(3, 1, '寿司青海', 'スシアオミ', 'Sushi Aomi', 'shop03.jpg', '新鮮な魚を使っています。', '12:00', '22:00', '水曜日', 8000, 15000, '愛知県名古屋市南区X-XX-XX', '052-111-1113', 10);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(4, 2, '天ぷら山里', 'テンプラヤマザト', 'Tempura Yamazato', 'shop04.jpg', '揚げたてサクサクの天ぷらを提供いたします。', '18:00', '23:00', '火曜日', 2000, 5000, '愛知県名古屋市西区X-XX-XX', '052-111-1114', 50);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(5, 1, '寿司山村', 'スシヤマムラ', 'Sushi Yamamura', 'shop05.jpg', 'お手頃価格のお寿司となっております。', '06:00', '23:00', '木曜日', 1000, 5000, '愛知県名古屋市東区X-XX-XX', '052-111-1115', 30);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(6, 3, 'かつ本', 'カツモト', 'Katsumoto', 'shop06.jpg', 'こだわりの銘柄豚を使用しております。', '11:00', '15:00', '金曜日', 500, 1500, '愛知県名古屋市中区X-XX-XX', '052-111-1116', 10);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(7, 4, '焼き鳥テツ', 'ヤキトリテツ', 'Yakitori Tetsu', 'shop07.jpg', '大人数のご利用も可能な焼き鳥居酒屋となっております。。', '17:00', '23:00', '月曜日', 1000, 10000, '愛知県名古屋市緑区X-XX-XX', '052-111-1117', 200);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(8, 5, 'しゃぶしゃぶ梓', 'シャブシャブアズサ', 'Shabu-Shabu Azusa', 'shop08.jpg', '出汁にこだわっております。', '17:00', '22:00', '日曜日', 5000, 10000, '愛知県名古屋市港区X-XX-OO', '052-111-1118', 50);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(9, 6, '蕎麦よしみつ', 'ソバヨシミツ', 'Soba Yoshimitsu', 'shop09.jpg', '肉蕎麦が売りとなっております。', '17:00', '06:00', '土曜日', 500, 2000, '愛知県名古屋市北区X-XX-OO', '052-111-1119', 5);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(10, 9, 'Dumbo', 'ダンボ', 'Dumbo', 'shop10.jpg', '昔懐かしい洋食店となっております。', '11:00', '21:00', '不定休', 500, 1500, '愛知県名古屋市南区X-XX-OO', '052-111-1110', 15);
INSERT IGNORE INTO shops(id, category_id, shop_name, furigana, alphabet, photo_name, description, opening_hour, closing_hour, closed_day, minimum_budget, maximum_budget, address, phone_number, seats)VALUE(11, 11, 'Trim', 'トリム', 'Trim', 'shop11.jpg', '窯焼きのピザがおすすめです。', '12:00', '22:00', '水曜日', 2000, 5000, '愛知県名古屋市西区X-XX-OO', '052-111-1121', 25);

--reservesテーブル
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(1, 1, 1, '2024-06-01', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(2, 1, 1, '2024-06-02', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(3, 1, 1, '2024-06-03', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(4, 1, 1, '2024-06-04', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(5, 1, 1, '2024-06-05', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(6, 1, 1, '2024-06-06', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(7, 1, 2, '2024-06-01', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(8, 1, 1, '2024-06-01', '18:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(9, 1, 3, '2024-06-01', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(10, 1, 2, '2024-06-02', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(11, 1, 2, '2024-06-03', '17:00', 4);
INSERT IGNORE INTO reserves(id, user_id, shop_id, booking_date, booking_time, number_of_people)VALUE(12, 2, 1, '2024-06-01', '17:00', 4);

--reviewsテーブル
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(1, 1, 1, '★★★★★', '雰囲気のいいカフェでした。', 'shop01.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(2, 1, 2, '★★★★★', 'おいしかったです。', 'shop02.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(3, 1, 3, '★★★★★', 'よかったです。', 'shop03.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(4, 1, 4, '★★★★★', 'よかったです。', 'shop04.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(5, 1, 5, '★★★★★', '雰囲気のいいカフェでした。', 'shop05.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(6, 1, 6, '★★★★★', '雰囲気のいいカフェでした。', 'shop06.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(7, 1, 7, '★★★★★', '雰囲気のいいカフェでした。', 'shop07.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(8, 1, 8, '★★★★★', '雰囲気のいいカフェでした。', 'shop08.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(9, 1, 9, '★★★★★', '雰囲気のいいカフェでした。', 'shop09.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(10, 1, 10, '★★★★★', '雰囲気のいいカフェでした。', 'shop10.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(11, 1, 11, '★★★★★', '雰囲気のいいカフェでした。', 'shop11.jpg');
INSERT IGNORE INTO reviews(id, user_id, shop_id, stars, review_comment, photo_name)VALUE(12, 2, 1, '★★★☆☆', 'おいしかったです。', 'shop01.jpg');

--favoritesテーブル
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(1, 1, 1);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(2, 1, 2);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(3, 1, 3);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(4, 1, 4);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(5, 1, 5);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(6, 1, 6);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(7, 1, 7);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(8, 1, 8);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(9, 1, 9);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(10, 1, 10);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(11, 1, 11);
INSERT IGNORE INTO favorites(id, user_id, shop_id)VALUE(12, 2, 1);