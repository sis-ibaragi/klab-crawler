/**
 * 
 */
package klab.rslt.crawler.entity.ext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;

import lombok.Data;

/**
 * @author ibaragi
 *
 */
@Data
public class SelectTargetRaceListEntity {

	/** クラスパス上の SQL ファイルパス */
	private static final String SQL_PATH = "sql/select_target_race_list.sql";

	/** SQL */
	private static String sql;

	/** 開催コード */
	private String kaisaiCd;

	/** 開催日 */
	private LocalDate kaisaiDt;

	/** レース番号 */
	private Integer raceNo;

	/**
	 * このクラスが保持する SQL を返します。
	 * 
	 * @return SQL
	 */
	public static String getSql() {
		if (sql == null) {
			// SQL を読み込む
			try (InputStream is = SelectTargetRaceListEntity.class.getClassLoader().getResourceAsStream(SQL_PATH);
					BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line).append(System.lineSeparator());
				}
				sql = sb.toString();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return sql;
	}
}
