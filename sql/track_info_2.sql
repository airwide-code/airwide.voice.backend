INSERT INTO product_media (service_id, category_id, product_id, track_id, track_path, track_title_path, track_duration, play_count)
VALUES
(789, 3, 1, 1, '01_radio_alkaline',    '01_radio_alkaline_title', 5, 0),
(789, 3, 1, 2, '02_radio_sheila_on_7', '02_radio_sheila_on_7_title', 2, 0),
(789, 3, 1, 3, '03_radio_lana_del_rey','03_radio_lana_del_rey', 4, 0),

(789, 4, 1, 1, '01_2018_08_19_english_premier_results', '01_2018_08_19_english_premier_results_title', 14, 0),



(789, 4, 1, 2, '02_2018_08_18_english_premier_results', '02_2018_08_19_english_premier_results_title', 28, 0),
(789, 4, 2, 1, '01_2018_08_20_spanish_la_liga_results', '01_2018_08_20_spanish_la_liga_results_title', 8, 0),
(789, 4, 2, 2, '02_2018_08_19_spanish_la_liga_results', '02_2018_08_19_spanish_la_liga_results_title', 14, 0),
(789, 4, 2, 3, '03_2018_08_18_spanish_la_liga_results', '03_2018_08_18_spanish_la_liga_results_title', 9, 0),
(789, 4, 2, 4, '04_2018_08_17_spanish_la_liga_results', '04_2018_08_17_spanish_la_liga_results_title', 10, 0),

(789, 5, 1, 1, '01_salt_water_rinse', '01_salt_water_rinse_title', 20, 0),
(789, 5, 1, 2, '02_hydrogen_peroxide_rinse', '02_hydrogen_peroxide_rinse_title', 21, 0),
(789, 5, 1, 3, '03_cold_compress', '03_cold_compress_title', 27, 0),
(789, 5, 1, 4, '04_peppermint_tea_bags', '04_peppermint_tea_bags_title', 10, 0),
(789, 5, 1, 5, '05_garlic', '05_garlic_title', 24, 0),

(789, 6, 1234, 1, '01_exam_results_abel_thomson', '01_exam_results_abel_thomson_title', 17, 0),
(789, 6, 5678, 1, '02_exam_results_betty_michaels', '02_exam_results_betty_michaels_title', 26, 0),
(789, 6, 2468, 1, '03_exam_results_maria_jones', '03_exam_results_maria_jones_title', 9, 0),
(789, 6, 0246, 1, '04_exam_results_peter_mathews', '04_exam_results_peter_mathews_title', 10, 0),

(789, 7, 1, 1, '01_informal_markets_gmb_commodity_prices', '01_informal_markets_gmb_commodity_prices_title', 32, 0),

(789, 7, 2, 1, '01_informal_markets_private_commodity_prices', '01_informal_markets_private_commodity_prices_title', 34, 0);

sudo ffmpeg -i 03_2018_08_18_spanish_la_liga_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_2018_08_18_spanish_la_liga_results.wav
sudo ffmpeg -i 04_2018_08_17_spanish_la_liga_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_2018_08_17_spanish_la_liga_results.wav
sudo ffmpeg -i 04_peppermint_tea_bags.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_peppermint_tea_bags.wav
sudo ffmpeg -i 04_exam_results_peter_mathews.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_exam_results_peter_mathews.wav
sudo ffmpeg -i 01_radio_alkaline.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_radio_alkaline.wav
sudo ffmpeg -i 02_radio_sheila_on_7.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_radio_sheila_on_7.wav
sudo ffmpeg -i 03_radio_lana_del_rey.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_radio_lana_del_rey.wav
sudo ffmpeg -i 01_2018_08_19_english_premier_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_2018_08_19_english_premier_results.wav


root@eBridgeIVRGateway2:/tmp/sounds# sudo ffmpeg -i 03_2018_08_18_spanish_la_liga_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_2018_08_18_spanish_la_liga_results.wav
sudo ffmpeg -i 04_exam_results_peter_mathews.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_exam_results_peter_mathews.wav
  Duration: 00:00:08.44, start: 0.000000, bitrate: 69 kb/s
root@eBridgeIVRGateway2:/tmp/sounds# udo ffmpeg -i 04_2018_08_17_spanish_la_liga_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_2018_08_17_spanish_la_liga_results.wav
sudo ffmpeg -i 03_radio_lana_del_rey.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_radio_lana_del_rey.wav
sudo ffmpeg -i 01_2018_08_19_english_premier_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_2018_08_19_english_premier_results.wavThe program 'udo' is currently not installed. You can install it by typing:
root@eBridgeIVRGateway2:/tmp/sounds# sudo ffmpeg -i 04_peppermint_tea_bags.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_peppermint_tea_bags.wav
  Duration: 00:00:25.48, start: 0.000000, bitrate: 71 kb/s
root@eBridgeIVRGateway2:/tmp/sounds# udo ffmpeg -i 04_exam_results_peter_mathews.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_exam_results_peter_mathews.wav
root@eBridgeIVRGateway2:/tmp/sounds# sudo ffmpeg -i 01_radio_alkaline.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_radio_alkaline.wav
  Duration: 00:04:40.40, start: 0.000000, bitrate: 48 kb/s
root@eBridgeIVRGateway2:/tmp/sounds# p3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_radio_sheila_on_7.wav
root@eBridgeIVRGateway2:/tmp/sounds# sudo ffmpeg -i 03_radio_lana_del_rey.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_radio_lana_del_rey.wav
  Duration: 00:03:37.60, start: 0.000000, bitrate: 128 kb/s
File '/var/lib/asterisk/sounds/en/03_radio_lana_del_rey.wav' already exists. Overwrite ? [y/N] y



# sudo ffmpeg -i 02_radio_sheila_on_7_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_radio_sheila_on_7_title.wav
  Duration: 00:00:01.72, start: 0.000000, bitrate: 70 kb/s
b# sudo ffmpeg -i 02_hydrogen_peroxide_rinse.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_hydrogen_peroxide_rinse.wav
  Duration: 00:00:20.54, start: 0.000000, bitrate: 75 kb/s
# sudo ffmpeg -i 02_hydrogen_peroxide_rinse_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_hydrogen_peroxide_rinse_title.wav
  Duration: 00:00:01.88, start: 0.000000, bitrate: 74 kb/s
# udo ffmpeg -i 04_peppermint_tea_bags_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_peppermint_tea_bags_title.wav
# sudo ffmpeg -i 05_garlic.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/05_garlic.wav
  Duration: 00:00:23.35, start: 0.000000, bitrate: 74 kb/s
# udo ffmpeg -i 05_garlic_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/05_garlic_title.wav
# sudo ffmpeg -i 01_salt_water_rinse_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_salt_water_rinse_title.wav
  Duration: 00:00:01.67, start: 0.000000, bitrate: 57 kb/s
# sudo ffmpeg -i 01_informal_markets_private_commodity_prices_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_informal_markets_private_commodity_prices_title.wav
  Duration: 00:00:02.83, start: 0.000000, bitrate: 74 kb/s
# sudo ffmpeg -i exam_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/exam_results.wav
  Duration: 00:00:01.55, start: 0.000000, bitrate: 75 kb/s
# sudo ffmpeg -i 03_2018_08_18_spanish_la_liga_results_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_2018_08_18_spanish_la_liga_results_title.wav
  Duration: 00:00:03.24, start: 0.000000, bitrate: 74 kb/s
# sudo ffmpeg -i 01_radio_alkaline_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_radio_alkaline_title.wav
# sudo ffmpeg -i 03_cold_compress.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_cold_compress.wav
  Duration: 00:00:26.16, start: 0.000000, bitrate: 70 kb/s
# udo ffmpeg -i informal_markets.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/informal_markets.wav
# sudo ffmpeg -i 01_salt_water_rinse.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_salt_water_rinse.wav
  Duration: 00:00:19.82, start: 0.000000, bitrate: 66 kb/s
# udo ffmpeg -i 01_salt_water_rinse.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_salt_water_rinse.wav
# sudo ffmpeg -i 03_cold_compress_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_cold_compress_title.wav
  Duration: 00:00:01.59, start: 0.000000, bitrate: 56 kb/s
# sudo ffmpeg -i 03_exam_results_maria_jones.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_exam_results_maria_jones.wav
  Duration: 00:00:08.42, start: 0.000000, bitrate: 36 kb/s
# udo ffmpeg -i 03_exam_results_maria_jones_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/03_exam_results_maria_jones_title.wav
# sudo ffmpeg -i 02_exam_results_betty_michaels.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_exam_results_betty_michaels.wav
  Duration: 00:00:25.99, start: 0.000000, bitrate: 31 kb/s
# sudo ffmpeg -i 01_exam_results_abel_thomson.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_exam_results_abel_thomson.wav
  Duration: 00:00:16.70, start: 0.000000, bitrate: 42 kb/s
# udo ffmpeg -i 02_exam_results_betty_michaels_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_exam_results_betty_michaels_title.wav
# sudo ffmpeg -i 04_exam_results_peter_mathews_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_exam_results_peter_mathews_title.wav
  Duration: 00:00:02.93, start: 0.000000, bitrate: 74 kb/s
# udo ffmpeg -i 01_exam_results_abel_thomson_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_exam_results_abel_thomson_title.wav
# sudo ffmpeg -i 02_2018_08_18_english_premier_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_2018_08_18_english_premier_results.wav
  Duration: 00:00:27.59, start: 0.000000, bitrate: 46 kb/s
# udo ffmpeg -i 01_2018_08_19_english_premier_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_2018_08_19_english_premier_results.wav
# sudo ffmpeg -i 02_2018_08_19_english_premier_results_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_2018_08_19_english_premier_results_title.wav
  Duration: 00:00:03.17, start: 0.000000, bitrate: 72 kb/s
# udo ffmpeg -i 01_2018_08_19_english_premier_results_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_2018_08_19_english_premier_results_title.wav
# sudo ffmpeg -i 02_2018_08_19_spanish_la_liga_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_2018_08_19_spanish_la_liga_results.wav
  Duration: 00:00:13.63, start: 0.000000, bitrate: 38 kb/s
# sudo ffmpeg -i 01_2018_08_20_spanish_la_liga_results.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_2018_08_20_spanish_la_liga_results.wav
  Duration: 00:00:08.06, start: 0.000000, bitrate: 50 kb/s
# udo ffmpeg -i 02_2018_08_19_spanish_la_liga_results_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/02_2018_08_19_spanish_la_liga_results_title.wav
# sudo ffmpeg -i 04_2018_08_17_spanish_la_liga_results_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/04_2018_08_17_spanish_la_liga_results_title.wav
  Duration: 00:00:03.61, start: 0.000000, bitrate: 71 kb/s
# udo ffmpeg -i 01_2018_08_20_spanish_la_liga_results_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_2018_08_20_spanish_la_liga_results_title.wav
# sudo ffmpeg -i 01_informal_markets_gmb_commodity_prices.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_informal_markets_gmb_commodity_prices.wav
  Duration: 00:00:32.33, start: 0.000000, bitrate: 35 kb/s
# udo ffmpeg -i 01_informal_markets_gmb_commodity_prices_title.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_informal_markets_gmb_commodity_prices_title.wav
# sudo ffmpeg -i 01_informal_markets_private_commodity_prices.mp3 -ar 8000 -ac 1 /var/lib/asterisk/sounds/en/01_informal_markets_private_commodity_prices.wav
  Duration: 00:00:33.54, start: 0.000000, bitrate: 33 kb/s
#
