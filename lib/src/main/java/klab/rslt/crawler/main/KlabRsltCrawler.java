/**
 * 
 */
package klab.rslt.crawler.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;

import klab.rslt.crawler.entity.ext.SelectTargetRaceListEntity;
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
		/** データベース */
		private String database;

		/** ユーザー */
		private String username;

		/** パスワード */
		private String password;

		/**
		 * コンストラクタ.
		 */
		private DatabaseProperties() {
			// database.properties の設定値を読み込む
			try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("database.properties");
					BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				Properties properties = new Properties();
				properties.load(br);
				// このクラスの変数に設定する
				this.database = properties.getProperty("database");
				this.username = properties.getProperty("username");
				this.password = properties.getProperty("password");
			} catch (IOException e) {
				throw new RuntimeException("プロパティファイルの読み込み時にエラーが発生しました。", e);
			}
		}
	}

	/** databbase.properties */
	private DatabaseProperties dbProperties;

	/**
	 * コンストラクタ.
	 */
	public KlabRsltCrawler() {
		// DB 接続情報を取得する
		this.dbProperties = new DatabaseProperties();
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
			log.error("処理中にエラーが発生しました。", e);
		} finally {
			log.info("処理が終了しました。");
		}
	}

	/**
	 * スクレイピングと DB への保存を実行します。
	 */
	private void execute() {
		// DB へ接続する
		Jdbi jdbi = Jdbi.create(this.dbProperties.getDatabase(), this.dbProperties.getUsername(),
				this.dbProperties.getPassword());

		// 成功・失敗件数のカウンター
		AtomicInteger successCnt = new AtomicInteger();
		AtomicInteger failureCnt = new AtomicInteger();
		try {
			jdbi.useHandle(handle -> {
				handle.useTransaction(h -> {
					// レース結果を取得する対象リストを取得する
					handle.registerRowMapper(BeanMapper.factory(SelectTargetRaceListEntity.class));
					handle.select(SelectTargetRaceListEntity.getSql()).mapTo(SelectTargetRaceListEntity.class)
							.forEach(entity -> {
								try {
									// 対象 Web ページを解析してレース結果・払戻金を取得する
									KlabDbRacePage page = new KlabDbRacePage(entity.getKaisaiCd(), entity.getKaisaiDt(),
											entity.getRaceNo()).parse();

									// TODO 取得した情報を DB へ保存する
									page.getRsltModel().insertRaceRsltList(handle);
									page.getDividendModel().insertaceRsltDividend(handle);

									// 成功件数をカウントアップする
									successCnt.incrementAndGet();
								} catch (Exception e) {
									log.error("処理中にエラーが発生しましたが、処理を続行します。", e);
									// 5 件以上エラーが発生した場合は終了する
									if (failureCnt.incrementAndGet() >= 5) {
										throw new RuntimeException("処理中に 5 件以上エラーが発生したため終了します。");
									}
								}
							});
					// コミットする
					handle.commit();
				});
			});
		} finally {
			// 成功・失敗件数をログに出力する
			if (failureCnt.intValue() == 0) {
				log.info("成功件数: {} 件, 失敗件数: {} 件", successCnt.intValue(), failureCnt.intValue());
			} else {
				log.warn("成功件数: {} 件, 失敗件数: {} 件", successCnt.intValue(), failureCnt.intValue());
			}
		}
	}
}
