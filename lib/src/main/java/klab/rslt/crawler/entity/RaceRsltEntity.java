/**
 * 
 */
package klab.rslt.crawler.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import klab.rslt.crawler.entity.ext.SelectTargetRaceListEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * @author ibaragi
 *
 */
@Data
@AllArgsConstructor
public class RaceRsltEntity {

	/** クラスパス上の SQL ファイルパス */
	private static final String INSERT_SQL_PATH = "sql/insert_race_rslt.sql";

	/** SQL */
	private static String insertSql;

	/** 開催コード */
	@NonNull
	private String kaisaiCd;

	/** レース番号 */
	@NonNull
	private Integer raceNo;

	/** 馬番 */
	@NonNull
	private Integer umaNo;

	/** 着順 */
	private Integer orderNo;

	/**
	 * このクラスが保持する INSERT 用 SQL を返します。
	 * 
	 * @return SQL
	 */
	public static String getInsertSql() {
		// 初回の呼び出し時に SQL を読み込む
		if (insertSql == null) {
			try (InputStream is = SelectTargetRaceListEntity.class.getClassLoader()
					.getResourceAsStream(INSERT_SQL_PATH);
					BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append(System.lineSeparator());
				}
				insertSql = sb.toString();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return insertSql;
	}
}
