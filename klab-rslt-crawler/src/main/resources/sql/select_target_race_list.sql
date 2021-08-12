select
	ka.KAISAI_CD,
	ka.KAISAI_DT,
	ra.RACE_NO
from
	KAISAI ka
	inner join RACE ra
		on	ka.KAISAI_CD = ra.KAISAI_CD
where
		ra.RACE_RSLT_DONE_FLG = 0
	and	ra.RACE_CANCEL_FLG = 0
order by
	KAISAI_CD,
	RACE_NO