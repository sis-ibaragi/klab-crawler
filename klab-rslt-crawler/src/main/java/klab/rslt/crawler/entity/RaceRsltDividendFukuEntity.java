package klab.rslt.crawler.entity;

import klab.rslt.crawler.util.FileUtels;
import lombok.Value;

/**
 * RACE_RSLT_DIVIDEND_FUKU テーブルの Entity クラスです。
 * 
 * @author ibaragi
 */
@Value
public class RaceRsltDividendFukuEntity {

	/** クラスパス上の SQL ファイルパス */
	private static final String INSERT_SQL_PATH = "sql/insert_race_rslt_dividend_fuku.sql";

	/** SQL: insert */
	private static String insertSql;

	/** 開催コード */
	private String kaisaiCd;

	/** レース番号 */
	private Integer raceNo;

	/** 馬番 */
	private Integer umaNo;

	/** 払戻金 */
	private Integer dividendYen;

	/**
	 * このクラスが保持する INSERT 用 SQL を返します。
	 * 
	 * @return SQL
	 */
	public static String getInsertSql() {
		// 初回の呼び出し時に SQL を読み込む
		if (insertSql == null) {
			insertSql = FileUtels.getFileContentsFromClasspath(INSERT_SQL_PATH);
		}
		return insertSql;
	}
}
