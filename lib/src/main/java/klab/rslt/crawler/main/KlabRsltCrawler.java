/**
 * 
 */
package klab.rslt.crawler.main;

import java.io.IOException;
import java.util.Properties;

import klab.rslt.crawler.page.KlabDbRacePage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 競馬ラボのレース結果ページをスクレイピングするメインクラスです。
 * 
 * @author ibaragi
 */
@Slf4j
public class KlabRsltCrawler {

	/**
	 * database.properties のデータ構造を表すクラスです。
	 */
	@Data
	private static class DatabaseProperties {
		private String database;
		private String username;
		private String password;

		private DatabaseProperties(Properties properties) {
			setDatabase(properties.getProperty("database"));
			setUsername(properties.getProperty("username"));
			setPassword(properties.getProperty("password"));
		}
	}

	/** databbase.properties */
	private DatabaseProperties dbProperties;

	/**
	 * コンストラクタ.
	 */
	public KlabRsltCrawler() {
		try {
			Properties properties = new Properties();
			properties.load(this.getClass().getClassLoader().getResourceAsStream("database.properties"));
			this.dbProperties = new DatabaseProperties(properties);
		} catch (IOException e) {
			log.error("プロパティファイルの読み込み時にエラーが発生しました。", e);
		}
	}

	/**
	 * このクラスのメインメソッドです。
	 * 
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		log.info("処理を開始します。");
		KlabRsltCrawler main = new KlabRsltCrawler();
		try {
			main.execute();
		} catch (Exception e) {
			log.error("実行中にエラーが発生しました。", e);
		} finally {
			log.info("処理が終了しました。");
		}
	}

	/**
	 * .
	 */
	private void execute() {
		new KlabDbRacePage("202103NG06", "20210808", 8).parse();
	}
}
