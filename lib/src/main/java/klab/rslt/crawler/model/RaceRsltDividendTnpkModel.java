/**
 * 
 */
package klab.rslt.crawler.model;

import lombok.Data;

/**
 * 単複の払戻金を保持するクラスです。
 * 
 * @author ibaragi
 */
@Data
public class RaceRsltDividendTnpkModel {

	/** 馬番 */
	private String umaNo;

	/** 払戻金 */
	private String dividendYen;

	/**
	 * 馬番を Integer 型に変換して返します。
	 * 
	 * @return 馬番（Integer 型）
	 */
	public Integer getUmaNoInt() {
		return Integer.valueOf(this.umaNo);
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
