/**
 * 
 */
package klab.rslt.crawler.page;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ibaragi
 *
 */
@Slf4j
public class KlabDbRacePage {

	/** ベース URL */
	private static final String BASE_URL = "https://www.keibalab.jp/db/race/%s/";

	/** 競馬場コード（独自）と競馬場コード（競馬ラボ）の Map */
	public static Map<String, String> keibajoNmCdMap;
	static {
		Map<String, String> map = new HashMap<>();
		map.put("SP", "01"); // 札幌
		map.put("HK", "02"); // 函館
		map.put("FK", "03"); // 福島
		map.put("NG", "04"); // 新潟
		map.put("TK", "05"); // 東京
		map.put("NK", "06"); // 中山
		map.put("CK", "07"); // 中京
		map.put("KY", "08"); // 京都
		map.put("HN", "09"); // 阪神
		map.put("KK", "10"); // 小倉
		keibajoNmCdMap = Collections.unmodifiableMap(map);
	}

	/** アクセス先 URL */
	private String url;

	/** ページコンテンツ */
	private Document document;

	/**
	 * コンストラクタ.
	 * @param kaisaiYmd 開催日
	 * @param keibajoCd 競馬場コード（独自）
	 * @param raceNo レース番号
	 */
	public KlabDbRacePage(String kaisaiYmd, String keibajoCd, int raceNo) {
		// URL を取得する
		this.url = String.format(BASE_URL, kaisaiYmd + keibajoNmCdMap.get(keibajoCd) + String.format("%02d", raceNo));
		// URL へアクセスしてページコンテンツを取得する
		try {
			this.document = Jsoup.connect(this.url).timeout(30_000).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ページを解析して必要なデータを取得します。
	 * @return このクラスのインスタンス
	 */
	public KlabDbRacePage parse() {
		this.document.select("table.resulttable tbody tr").stream().map(element -> {
			Elements cols = element.select("td");
			log.debug("着順: {}", cols.get(0).text()); // 着順
			log.debug("枠番: {}", cols.get(1).text()); // 枠番
			log.debug("馬番: {}", cols.get(2).text()); // 馬番
			log.debug("馬名: {}", cols.get(3).text()); // 馬名
			log.debug("性齢: {}", cols.get(4).text()); // 性齢
			log.debug("斤量: {}", cols.get(5).text()); // 斤量
			log.debug("騎手: {}", cols.get(6).text()); // 騎手
			log.debug("人気: {}", cols.get(7).text()); // 人気
			log.debug("単勝オッズ: {}", cols.get(8).text()); // 単勝オッズ
			log.debug("タイム: {}", cols.get(9).text()); // タイム
			log.debug("着差: {}", cols.get(10).text()); // 着差
			log.debug("通過順: {}", cols.get(11).text()); // 通過順
			log.debug("上りタイム: {}", cols.get(12).text()); // 上りタイム
			log.debug("調教師: {}", cols.get(13).text()); // 調教師
			log.debug("馬体重: {}", cols.get(14).text()); // 馬体重
			return null;
		}).collect(Collectors.toList());
		return this;
	}
	
}
