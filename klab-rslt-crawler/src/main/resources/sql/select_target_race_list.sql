-- サンプル SQL
select
	ka.KAISAI_CD,
	ka.KAISAI_DT,
	ra.RACE_NO
from
	KAISAI ka
	inner join RACE ra
		on	ka.KAISAI_CD = ra.KAISAI_CD
where
	not exists (
		select
			1
		from
			RACE_RSLT rs
		where
				rs.KAISAI_CD = ka.KAISAI_CD
			and rs.RACE_NO = ra.RACE_NO
	)
order by
	KAISAI_CD,
	RACE_NO