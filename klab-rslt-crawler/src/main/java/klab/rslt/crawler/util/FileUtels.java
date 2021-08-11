/**
 * 
 */
package klab.rslt.crawler.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ファイル入出力に関するユーティリティクラスです。
 * @author ibaragi
 */
public class FileUtels {

	/**
	 * コンストラクタ.
	 */
	private FileUtels() {
		// コンストラクタを隠蔽
	}

	/**
	 * 指定されたパス（クラスパス）からファイルの内容を読み込み、その内容を返します。
	 * @param path ファイルパス（クラスパス前提）
	 * @return ファイルから読み込んだ文字列
	 */
	public static String getFileContentsFromClasspath(String path) {
		// 指定されたパス（クラスパス）からファイルの内容を読み込む
		try (InputStream is = FileUtels.class.getClassLoader().getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append(System.lineSeparator());
			}
			// 読み込んだ文字列を返す
			return sb.toString();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
