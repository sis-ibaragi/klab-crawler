/**
 * 
 */
package klab.rslt.crawler.page;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import klab.rslt.crawler.model.RaceRsltDividendModel;
import klab.rslt.crawler.model.RaceRsltDividendTnpkModel;
import klab.rslt.crawler.model.RaceRsltDividendTtioModel;
import klab.rslt.crawler.model.RaceRsltDividendUmrnModel;
import klab.rslt.crawler.model.RaceRsltListModel;
import klab.rslt.crawler.model.RaceRsltModel;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 競馬ラボのレース情報ページに対応するクラスです。
 * 
 * @author ibaragi
 */
@Slf4j
@Data
public class KlabDbRacePage {

	/** ベース URL */
	private static final String BASE_URL = "https://www.keibalab.jp/db/race/%s/";

	/** 競馬場コード（独自）と競馬場コード（競馬ラボ）の Map */
	private static Map<String, String> keibajoNmCdMap;
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

	/** 開催コード */
	private String kaisaiCd;

	/** レース番号 */
	private int raceNo;

	/** レース結果 */
	private RaceRsltListModel rsltModel;

	/** レース払い戻し */
	private RaceRsltDividendModel dividendModel;

	/**
	 * コンストラクタ.
	 * 
	 * @param kaisaiYmd 開催日
	 * @param keibajoCd 競馬場コード（独自）
	 * @param raceNo    レース番号
	 */
	public KlabDbRacePage(@NonNull String kaisaiCd, @NonNull String kaisaiYmd, int raceNo) {
		// パラメータを取得
		this.kaisaiCd = kaisaiCd;
		this.raceNo = raceNo;

		// URL を取得する
		this.url = String.format(BASE_URL,
				kaisaiYmd + keibajoNmCdMap.get(kaisaiCd.substring(6, 8)) + String.format("%02d", raceNo));
		log.debug(this.url);
		// URL へアクセスしてページコンテンツを取得する
		try {
			this.document = Jsoup.connect(this.url).timeout(30_000).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ページを解析して必要なデータを取得します。
	 * 
	 * @return このクラスのインスタンス
	 */
	public KlabDbRacePage parse() {
		// レース結果を RaceRsltListModel へ設定する
		this.rsltModel = new RaceRsltListModel(this.kaisaiCd, this.raceNo);
		this.rsltModel.setRaceRsltList(this.document.select("table.resulttable tbody tr").stream().map(element -> {
			Elements cols = element.select("td");
			RaceRsltModel model = new RaceRsltModel();
			model.setOrderNo(cols.get(0).text()); // 着順
			model.setWakuNo(cols.get(1).text()); // 枠番
			model.setUmaNo(cols.get(2).text()); // 馬番
			model.setUmaNm(cols.get(3).text()); // 馬名
			model.setSeiRei(cols.get(4).text()); // 性齢
			model.setJockeyWeight(cols.get(5).text()); // 斤量
			model.setJockeyNm(cols.get(6).text()); // 騎手
			model.setNinkiNo(cols.get(7).text()); // 人気
			model.setTanOdds(cols.get(8).text()); // 単勝オッズ
			model.setTime(cols.get(9).text()); // タイム
			model.setChakusa(cols.get(10).text()); // 着差
			model.setTsukaOrder(cols.get(11).text()); // 通過順
			model.setLastF3Time(cols.get(12).text()); // 上りタイム
			model.setTrainerNm(cols.get(13).text()); // 調教師
			model.setHorseWeight(cols.get(14).text()); // 馬体重
			return model;
		}).collect(Collectors.toList()));
		// log.debug(rsltModel.toString());

		// 払い戻しを RaceRsltDividendModel へ設定する
		this.dividendModel = new RaceRsltDividendModel(this.kaisaiCd, this.raceNo);
		this.document.select("div.haraimodoshi table tr").stream().forEach(element -> {
			Elements cols = element.select("td");
			String firstCol = cols.get(0).text();
			if (firstCol.equals("単勝")) {
				// 単勝
				{
					String[] umaNoArr = cols.get(1).text().split("\s");
					String[] divYenArr = cols.get(2).text().split("\s");
					dividendModel.setTanList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
						RaceRsltDividendTnpkModel model = new RaceRsltDividendTnpkModel();
						model.setUmaNo(umaNoArr[i]);
						model.setDividendYen(divYenArr[i]);
						return model;
					}).collect(Collectors.toList()));
				}
				// 馬単
				{
					String[] umaNoArr = cols.get(4).text().split("\s");
					String[] divYenArr = cols.get(5).text().split("\s");
					dividendModel.setUmtnList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
						RaceRsltDividendUmrnModel model = new RaceRsltDividendUmrnModel();
						model.setUmaNoSeq(umaNoArr[i]);
						model.setDividendYen(divYenArr[i]);
						return model;
					}).collect(Collectors.toList()));
				}
			} else if (firstCol.equals("複勝")) {
				// 複勝
				{
					String[] umaNoArr = cols.get(1).text().split("\s");
					String[] divYenArr = cols.get(2).text().split("\s");
					dividendModel.setFukuList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
						RaceRsltDividendTnpkModel model = new RaceRsltDividendTnpkModel();
						model.setUmaNo(umaNoArr[i]);
						model.setDividendYen(divYenArr[i]);
						return model;
					}).collect(Collectors.toList()));
				}
				// ワイド
				{
					String[] umaNoArr = cols.get(4).text().split("\s");
					String[] divYenArr = cols.get(5).text().split("\s");
					dividendModel.setWideList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
						RaceRsltDividendUmrnModel model = new RaceRsltDividendUmrnModel();
						model.setUmaNoSeq(umaNoArr[i]);
						model.setDividendYen(divYenArr[i]);
						return model;
					}).collect(Collectors.toList()));
				}
			} else if (firstCol.equals("枠連")) {
				// 3 連複
				String[] umaNoArr = cols.get(4).text().split("\s");
				String[] divYenArr = cols.get(5).text().split("\s");
				dividendModel.setTrioList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
					RaceRsltDividendTtioModel model = new RaceRsltDividendTtioModel();
					model.setUmaNoSeq(umaNoArr[i]);
					model.setDividendYen(divYenArr[i]);
					return model;
				}).collect(Collectors.toList()));
			} else if (firstCol.equals("馬連")) {
				// 馬連
				{
					String[] umaNoArr = cols.get(1).text().split("\s");
					String[] divYenArr = cols.get(2).text().split("\s");
					dividendModel.setUmrnList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
						RaceRsltDividendUmrnModel model = new RaceRsltDividendUmrnModel();
						model.setUmaNoSeq(umaNoArr[i]);
						model.setDividendYen(divYenArr[i]);
						return model;
					}).collect(Collectors.toList()));
				}
				// 3 連単
				{
					String[] umaNoArr = cols.get(4).text().split("\s");
					String[] divYenArr = cols.get(5).text().split("\s");
					dividendModel.setTrifectaList(IntStream.range(0, umaNoArr.length).mapToObj(i -> {
						RaceRsltDividendTtioModel model = new RaceRsltDividendTtioModel();
						model.setUmaNoSeq(umaNoArr[i]);
						model.setDividendYen(divYenArr[i]);
						return model;
					}).collect(Collectors.toList()));
				}
			}
		});
		// log.debug(divModel.toString());
		return this;
	}

}
