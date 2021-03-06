/**
 * 
 */
package klab.rslt.crawler.model;

import java.util.ArrayList;
import java.util.List;

import org.jdbi.v3.core.Handle;

import klab.rslt.crawler.entity.RaceEntity;
import klab.rslt.crawler.entity.RaceRsltEntity;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * レース結果を保持するクラスです。
 * 
 * @author ibaragi
 */
@Data
@Slf4j
public class RaceRsltListModel {

	/** 開催コード */
	@NonNull
	private String kaisaiCd;

	/** レース番号 */
	@NonNull
	private Integer raceNo;

	/** RaceRsltModel の List */
	private List<RaceRsltModel> raceRsltList = new ArrayList<>();

	/**
	 * パラメータの RaceRsltModel をこのクラスが保持する RaceRsltModel の List へ追加します。
	 * 
	 * @param model RaceRsltModel
	 */
	public void addRaceRsltModel(RaceRsltModel model) {
		this.raceRsltList.add(model);
	}

	/**
	 * RaceRsltModel の List を RACE_RSLT テーブルへレコードを登録します。
	 * 
	 * @param handle Handle
	 */
	public void insertRaceRsltList(Handle handle) {
		this.raceRsltList.forEach(model -> {
			log.debug("レース結果: {}", model.toString());
			// INSERT を実行する
			handle.createUpdate(RaceRsltEntity.getInsertSql()).bindBean(new RaceRsltEntity(this.kaisaiCd, this.raceNo,
					model.getUmaNoInt(), model.getOrderNoInt(), model.getRaceRsltDiv())).execute();
		});
	}

	/**
	 * RACE テーブルの RACE_RSLT_DONE_FLG を ON に更新します。
	 * 
	 * @param handle Handle
	 */
	public void updateRaceRsltDoneFlg(Handle handle) {
		// UPDATE を実行する
		handle.createUpdate(RaceEntity.getUpdateSql())
				.bindBean(new RaceEntity(this.kaisaiCd, this.raceNo, Boolean.TRUE)).execute();
	}
}
