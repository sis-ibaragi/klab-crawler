/**
 * 
 */
package klab.rslt.crawler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

/**
 * @author ibaragi
 *
 */
@Data
public class RaceRsltListModel {

	/** 開催コード */
	@NonNull
	private String kaisaiCd;

	/** レース番号 */
	@NonNull
	private Integer raceNo;

	/** RaceRsltModel の List */
	private List<RaceRsltModel> raceRsltList = new ArrayList<RaceRsltModel>();

	/**
	 * パラメータの RaceRsltModel をこのクラスが保持する RaceRsltModel の List へ追加します。
	 * 
	 * @param model RaceRsltModel
	 */
	public void addRaceRsltModel(RaceRsltModel model) {
		this.raceRsltList.add(model);
	}

	/**
	 * RaceRsltModel の List を RACE_RSLT テーブルへ登録します。
	 */
	public int insertRaceRsltList() {
		this.raceRsltList.forEach(mode -> {
			// TODO 未実装
		});
		return this.raceRsltList.size();
	}
}
