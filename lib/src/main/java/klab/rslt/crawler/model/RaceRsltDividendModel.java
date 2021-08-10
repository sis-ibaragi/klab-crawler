/**
 * 
 */
package klab.rslt.crawler.model;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.Handle;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * 各種払戻金を保持するクラスです。
 * 
 * @author ibaragi
 */
@Data
@Slf4j
public class RaceRsltDividendModel {

	/** 開催コード */
	@NonNull
	private String kaisaiCd;

	/** レース番号 */
	@NonNull
	private Integer raceNo;

	/** 単勝（同着を想定して List 型） */
	private List<RaceRsltDividendTnpkModel> tanList = new ArrayList<>();

	/** 複勝 */
	private List<RaceRsltDividendTnpkModel> fukuList = new ArrayList<>();

	/** 馬連（同着を想定して List 型） */
	private List<RaceRsltDividendUmrnModel> umrnList = new ArrayList<>();

	/** 馬単（同着を想定して List 型） */
	private List<RaceRsltDividendUmrnModel> umtnList = new ArrayList<>();

	/** ワイド */
	private List<RaceRsltDividendUmrnModel> wideList = new ArrayList<>();

	/** 3 連複（同着を想定して List 型） */
	private List<RaceRsltDividendTtioModel> trioList = new ArrayList<>();

	/** 3 連単（同着を想定して List 型） */
	private List<RaceRsltDividendTtioModel> trifectaList = new ArrayList<>();

	/**
	 * 払戻金を DB へ登録します。
	 * @param handle Jdbi の Handle クラスのインスタンス
	 */
	public void insertaceRsltDividend(Handle handle) {
		// 単勝
		this.tanList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert 単勝: {}", model.toString());
		});
		// 複勝
		this.fukuList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert 複勝: {}", model.toString());
		});
		// 馬連
		this.umrnList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert 馬連: {}", model.toString());
		});
		// 馬単
		this.umtnList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert 馬単: {}", model.toString());
		});
		// ワイド
		this.wideList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert ワイド: {}", model.toString());
		});
		// 3 連複
		this.trioList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert 3 連複: {}", model.toString());
		});
		// 3 連単
		this.trifectaList.forEach(model -> {
			// TODO 未実装
			log.debug("Insert 3 連単: {}", model.toString());
		});
	
	}
}
