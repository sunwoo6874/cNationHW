INSERT INTO category (body_type) VALUES
('미니SUV'),
('준중형SUV'),
('중형SUV'),
('경형RV'),
('대형RV'),
('중형트럭');

INSERT INTO automobile (manufacturer, model, year, status, rent_time) VALUES
('현대', '코나', '2024', 'available', '2025-03-07 10:00:00'),
('현대', '아이오닉', '2024', 'available', '2025-03-07 10:00:00'),
('현대', '스타리아', '2022', 'lost', '2025-03-07 10:00:00'),
('현대', '포터', '2024', 'available', '2025-03-07 10:00:00'),
('현대', '투싼', '2023', 'rented', '2025-03-07 10:00:00'),
('KIA', '카니발', '2021', 'repairing', '2025-03-07 10:00:00'),
('KIA', '레이', '2022', 'repairing', '2025-03-07 10:00:00'),
('KIA', '봉고3', '2023', 'available', '2025-03-07 10:00:00'),
('KIA', '쏘렌토', '2024', 'available', '2025-03-07 10:00:00');

INSERT INTO automobile_category (automobile_id, category_id) VALUES
(1, 1), -- 코나 -> 미니SUV
(2, 2), -- 아이오닉 -> 준중형SUV
(3, 5), -- 스타리아 -> 대형RV
(4, 6), -- 포터 -> 중형트럭
(5, 2), -- 투싼 -> 준중형SUV
(6, 5), -- 카니발 -> 대형RV
(7, 4), -- 레이 -> 경형RV
(8, 5), -- 봉고3 -> 중형트럭
(9, 3); -- 쏘렌토 -> 중형SUV