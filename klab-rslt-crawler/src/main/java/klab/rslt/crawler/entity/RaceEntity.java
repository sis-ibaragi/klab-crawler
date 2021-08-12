/**
 * 
 */
package klab.rslt.crawler.entity;

import klab.rslt.crawler.util.FileUtels;
import lombok.Value;

/**
 * RACE テーブルの Entity クラスです。
 * 
 * @author ibaragi
 */
@Value
public class RaceEntity {

	/** クラスパス上の SQL ファイルパス */
	private static final String UPDATE_SQL_PATH = "sql/update_race_rslt.sql";

	/** SQL: insert */
	private static String updateSql;

	/** 開催コード */
	private String kaisaiCd;

	/** レース番号 */
	private Integer raceNo;

	/** レース結果取得済みフラグ */
	private Boolean raceRsltDoneFlg;

	/**
	 * このクラスが保持する UPDATE 用 SQL を返します。
	 * 
	 * @return SQL
	 */
	public static String getUpdateSql() {
		// 初回の呼び出し時に SQL を読み込む
		if (updateSql == null) {
			updateSql = FileUtels.getFileContentsFromClasspath(UPDATE_SQL_PATH);
		}
		return updateSql;
	}
}
