/**
 * 
 */
package klab.rslt.crawler.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NonNull;

/**
 * 各種払戻金を保持するクラスです。
 * 
 * @author ibaragi
 */
@Data
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

}
