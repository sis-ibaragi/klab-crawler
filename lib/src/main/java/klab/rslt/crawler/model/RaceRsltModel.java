/**
 * 
 */
package klab.rslt.crawler.model;

import klab.rslt.crawler.constant.RaceRsltDiv;
import lombok.Data;

/**
 * レース結果の 1 レコードを保持するクラスです。
 * 
 * @author ibaragi
 */
@Data
public class RaceRsltModel {

	/** 着順 */
	private String orderNo;
	/** 枠番 */
	private String wakuNo;
	/** 馬番 */
	private String umaNo;
	/** 馬名 */
	private String umaNm;
	/** 性齢 */
	private String seiRei;
	/** 斤量 */
	private String jockeyWeight;
	/** 騎手 */
	private String jockeyNm;
	/** 人気 */
	private String ninkiNo;
	/** 単勝オッズ */
	private String tanOdds;
	/** タイム */
	private String time;
	/** 着差 */
	private String chakusa;
	/** 通過順 */
	private String tsukaOrder;
	/** 上りタイム */
	private String lastF3Time;
	/** 調教師 */
	private String trainerNm;
	/** 馬体重 */
	private String horseWeight;

	/**
	 * 着順を Integer 型に変換して返します。
	 * 
	 * @return 着順（Integer 型）
	 */
	public Integer getOrderNoInt() {
		try {
			return Integer.valueOf(this.orderNo);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 馬番を Integer 型に変換して返します。
	 * 
	 * @return 馬番（Integer 型）
	 */
	public Integer getUmaNoInt() {
		return Integer.valueOf(this.umaNo);
	}

	/**
	 * 着順のオリジナル値からレース結果区分を返します。
	 * 
	 * @return レース結果区分
	 */
	public Integer getRaceRsltDiv() {
		if (this.getOrderNoInt() == null) {
			return RaceRsltDiv.getByLabel(this.getOrderNo()).getId();
		}
		return RaceRsltDiv.FINISH.getId();
	}
}
