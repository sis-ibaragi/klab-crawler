/**
 * 
 */
package klab.rslt.crawler.model;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.Handle;

import klab.rslt.crawler.entity.RaceRsltDividendFukuEntity;
import klab.rslt.crawler.entity.RaceRsltDividendTanEntity;
import klab.rslt.crawler.entity.RaceRsltDividendTrifectaEntity;
import klab.rslt.crawler.entity.RaceRsltDividendTrioEntity;
import klab.rslt.crawler.entity.RaceRsltDividendUmrnEntity;
import klab.rslt.crawler.entity.RaceRsltDividendUmtnEntity;
import klab.rslt.crawler.entity.RaceRsltDividendWideEntity;
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
	 * 
	 * @param handle Jdbi の Handle クラスのインスタンス
	 */
	public void insertaceRsltDividend(Handle handle) {
		// 単勝
		this.tanList.forEach(model -> {
			log.debug("払戻金 - 単勝: {}", model.toString());
			handle.createUpdate(RaceRsltDividendTanEntity.getInsertSql())
					.bindBean(new RaceRsltDividendTanEntity(this.kaisaiCd, this.raceNo, model.getUmaNoInt(),
							model.getDividendYenInt()))
					.execute();
		});
		// 複勝
		this.fukuList.forEach(model -> {
			log.debug("払戻金 - 複勝: {}", model.toString());
			handle.createUpdate(RaceRsltDividendFukuEntity.getInsertSql())
					.bindBean(new RaceRsltDividendFukuEntity(this.kaisaiCd, this.raceNo, model.getUmaNoInt(),
							model.getDividendYenInt()))
					.execute();
		});
		// 馬連
		this.umrnList.forEach(model -> {
			log.debug("払戻金 - 馬連: {}", model.toString());
			handle.createUpdate(RaceRsltDividendUmrnEntity.getInsertSql())
					.bindBean(new RaceRsltDividendUmrnEntity(this.kaisaiCd, this.raceNo, model.getUmaNo1Int(),
							model.getUmaNo2Int(), model.getDividendYenInt()))
					.execute();
		});
		// 馬単
		this.umtnList.forEach(model -> {
			log.debug("払戻金 - 馬単: {}", model.toString());
			handle.createUpdate(RaceRsltDividendUmtnEntity.getInsertSql())
					.bindBean(new RaceRsltDividendUmtnEntity(this.kaisaiCd, this.raceNo, model.getUmaNo1Int(),
							model.getUmaNo2Int(), model.getDividendYenInt()))
					.execute();
		});
		// ワイド
		this.wideList.forEach(model -> {
			log.debug("払戻金 - ワイド: {}", model.toString());
			handle.createUpdate(RaceRsltDividendWideEntity.getInsertSql())
					.bindBean(new RaceRsltDividendWideEntity(this.kaisaiCd, this.raceNo, model.getUmaNo1Int(),
							model.getUmaNo2Int(), model.getDividendYenInt()))
					.execute();
		});
		// 3 連複
		this.trioList.forEach(model -> {
			log.debug("払戻金 - 3 連複: {}", model.toString());
			handle.createUpdate(RaceRsltDividendTrioEntity.getInsertSql())
					.bindBean(new RaceRsltDividendTrioEntity(this.kaisaiCd, this.raceNo, model.getUmaNo1Int(),
							model.getUmaNo2Int(), model.getUmaNo3Int(), model.getDividendYenInt()))
					.execute();
		});
		// 3 連単
		this.trifectaList.forEach(model -> {
			log.debug("払戻金 - 3 連単: {}", model.toString());
			handle.createUpdate(RaceRsltDividendTrifectaEntity.getInsertSql())
					.bindBean(new RaceRsltDividendTrifectaEntity(this.kaisaiCd, this.raceNo, model.getUmaNo1Int(),
							model.getUmaNo2Int(), model.getUmaNo3Int(), model.getDividendYenInt()))
					.execute();
		});
	}
}
