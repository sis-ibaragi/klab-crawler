/**
 * 
 */
package klab.rslt.crawler.model;

import lombok.Data;

/**
 * 馬連・馬単・ワイドの払戻金を保持するクラスです。
 * 
 * @author ibaragi
 */
@Data
public class RaceRsltDividendUmrnModel {

	/** 馬番 1 */
	private String umaNo1;

	/** 馬番 2 */
	private String umaNo2;

	/** 払戻金 */
	private String dividendYen;

	/**
	 * "5-12" などの馬番のリストを受け取り、馬番の各変数へ設定します。
	 */
	public void setUmaNoSeq(String umaNoSeq) {
		String[] umaNoArr = umaNoSeq.split("-");
		this.umaNo1 = umaNoArr[0];
		this.umaNo2 = umaNoArr[1];
	}

	/**
	 * 馬番 1 を Integer 型に変換して返します。
	 * 
	 * @return 馬番 1（Integer 型）
	 */
	public Integer getUmaNo1Int() {
		return Integer.valueOf(this.umaNo1);
	}

	/**
	 * 馬番 2 を Integer 型に変換して返します。
	 * 
	 * @return 馬番 2（Integer 型）
	 */
	public Integer getUmaNo2Int() {
		return Integer.valueOf(this.umaNo2);
	}

	/**
	 * 払戻金を Integer 型に変換して返します。
	 * 
	 * @return 払戻金（Integer 型）
	 */
	public Integer getDividendYenInt() {
		return Integer.valueOf(this.dividendYen.replaceAll("[,円]", ""));
	}
}
