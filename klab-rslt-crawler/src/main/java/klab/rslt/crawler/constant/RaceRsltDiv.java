/**
 * 
 */
package klab.rslt.crawler.constant;

import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * レース結果区分の Enum クラスです。
 * 
 * @author ibaragi
 */
@AllArgsConstructor
@Getter
public enum RaceRsltDiv {

	// 完走
	FINISH(0, ""),
	// 出走取消
	CANCEL(7, "取消"),
	// 競争除外
	EXCLUDE(8, "除外"),
	// 競争中止
	STOP(9, "中止");

	/** 区分値 */
	private int id;

	/** 区分名 */
	private String label;

	/**
	 * 区分名から該当する区分値の Enum を返します。
	 * 
	 * @param label 区分名
	 * @return 区分値の Enum
	 */
	public static RaceRsltDiv getByLabel(String label) {
		return Arrays.stream(RaceRsltDiv.values()).filter(e -> e.getLabel().equals(label)).findFirst().orElse(FINISH);
	}
}
