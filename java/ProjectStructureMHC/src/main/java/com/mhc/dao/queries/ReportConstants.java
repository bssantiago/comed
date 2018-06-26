package com.mhc.dao.queries;

public class ReportConstants {

	public static final String BLOOD_RESULTS_REPORT = "select " +
			"results.normal as normal," + 
			"results.normal_prev_1  as normal_prev_1," + 
			"results.normal_prev_2  as normal_prev_2," + 
			"results.normal_prev_3  as normal_prev_3," +  
			"results.preHypertension  as preHypertension," + 
			"results.preHypertension_prev_1  as preHypertension_prev_1," + 
			"results.preHypertension_prev_2  as preHypertension_prev_2," + 
			"results.preHypertension_prev_3  as preHypertension_prev_3," +			
			"results.stage1  as stage1," + 
			"results.stage1_prev_1  as stage1_prev_1," + 
			"results.stage1_prev_2  as stage1_prev_2," + 
			"results.stage1_prev_3  as stage1_prev_3," +			
			"results.stage2  as stage2," + 
			"results.stage2_prev_1  as stage2_prev_1," + 
			"results.stage2_prev_2  as stage2_prev_2," + 
			"results.stage2_prev_3  as stage2_prev_3" + 
			" from " + 
			"	(select	 " + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 3) ) and ((cast(cpb.sistolic as int) >= 0) and (cast(cpb.sistolic as int) < 120)) and ((cast(cpb.diastolic as int) >= 0) and (cast(cpb.diastolic as int) < 80)) then 1 else 0 end) normal_prev_3," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 3) ) and ((cast(cpb.sistolic as int) >= 120) and (cast(cpb.sistolic as int) <= 139)) and ((cast(cpb.diastolic as int) >= 80) and (cast(cpb.diastolic as int) <= 89)) then 1 else 0 end) preHypertension_prev_3," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 3) ) and ((cast(cpb.sistolic as int) >= 140) and (cast(cpb.sistolic as int) <= 159)) and ((cast(cpb.diastolic as int) >= 90) and (cast(cpb.diastolic as int) <= 99)) then 1 else 0 end) stage1_prev_3," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 3) ) and (cast(cpb.sistolic as int) >= 160) and ( (cast(cpb.diastolic as int) > 99)) then 1 else 0 end) stage2_prev_3," +  
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 2) ) and ((cast(cpb.sistolic as int) >= 0) and (cast(cpb.sistolic as int) < 120)) and ((cast(cpb.diastolic as int) >= 0) and (cast(cpb.diastolic as int) < 80)) then 1 else 0 end) normal_prev_2," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 2) ) and ((cast(cpb.sistolic as int) >= 120) and (cast(cpb.sistolic as int) <= 139)) and ((cast(cpb.diastolic as int) >= 80) and (cast(cpb.diastolic as int) <= 89)) then 1 else 0 end) preHypertension_prev_2," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 2) ) and ((cast(cpb.sistolic as int) >= 140) and (cast(cpb.sistolic as int) <= 159)) and ((cast(cpb.diastolic as int) >= 90) and (cast(cpb.diastolic as int) <= 99)) then 1 else 0 end) stage1_prev_2," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 2) ) and (cast(cpb.sistolic as int) >= 160) and ( (cast(cpb.diastolic as int) > 99)) then 1 else 0 end) stage2_prev_2," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 1) ) and ((cast(cpb.sistolic as int) >= 0) and (cast(cpb.sistolic as int) < 120)) and ((cast(cpb.diastolic as int) >= 0) and (cast(cpb.diastolic as int) < 80)) then 1 else 0 end) normal_prev_1," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 1) ) and ((cast(cpb.sistolic as int) >= 120) and (cast(cpb.sistolic as int) <= 139)) and ((cast(cpb.diastolic as int) >= 80) and (cast(cpb.diastolic as int) <= 89)) then 1 else 0 end) preHypertension_prev_1," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 1) ) and ((cast(cpb.sistolic as int) >= 140) and (cast(cpb.sistolic as int) <= 159)) and ((cast(cpb.diastolic as int) >= 90) and (cast(cpb.diastolic as int) <= 99)) then 1 else 0 end) stage1_prev_1," + 
			"	 sum(case when (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date) - 1) ) and (cast(cpb.sistolic as int) >= 160) and ( (cast(cpb.diastolic as int) > 99)) then 1 else 0 end) stage2_prev_1," +  
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date)) ) and ((cast(cpb.sistolic as int) >= 0) and (cast(cpb.sistolic as int) < 120)) and ((cast(cpb.diastolic as int) >= 0) and (cast(cpb.diastolic as int) < 80)) then 1 else 0 end) normal," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date)) ) and ((cast(cpb.sistolic as int) >= 120) and (cast(cpb.sistolic as int) <= 139)) and ((cast(cpb.diastolic as int) >= 80) and (cast(cpb.diastolic as int) <= 89)) then 1 else 0 end) preHypertension," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date)) ) and ((cast(cpb.sistolic as int) >= 140) and (cast(cpb.sistolic as int) <= 159)) and ((cast(cpb.diastolic as int) >= 90) and (cast(cpb.diastolic as int) <= 99)) then 1 else 0 end) stage1," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date)) ) and (cast(cpb.sistolic as int) >= 160) and ( (cast(cpb.diastolic as int) > 99)) then 1 else 0 end) stage2" +  
			"	 	from comed_participants_biometrics cpb " + 
			"		left join comed_participants cp on cp.id = cpb.participant_id" + 
			"		left join comed_client_assessment cpa on cpa.client_id = cp.client_id" + 
			"		where " + 
			"		cpa.status = true " + 
			"	 	and cast(cpb.sistolic as DECIMAL(6,2)) >= 0										" + 
			// "		-- and cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)" + 
			"	 ) as results";
	public static final String NEGATIVE_RESULTS_REPORT = "";
	public static final String POSITIVE_RESULTS_REPORT = "select" + 
			"results.SistolicDiastolic," + 
			"results.Tricerides," + 
			"results.Cholesterol," + 
			"results.HdlM," + 
			"results.HdlF," + 
			"results.Ldl" + 
			" from " + 
			"	(select" + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and (EXTRACT(YEAR FROM cpb.create_date) = (EXTRACT(YEAR FROM current_date)) ) and ((cast(cpb.sistolic as int) >= 0) and (cast(cpb.sistolic as int) < 120)) and ((cast(cpb.diastolic as int) >= 0) and (cast(cpb.diastolic as int) < 80)) then 1 else 0 end) SistolicDiastolic," + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and cpb.triglycerides < 150 then 1 else 0 end) Tricerides," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and cpb.cholesterol < 200 then 1 else 0 end) Cholesterol," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and cpb.hdl > 40 and cp.gender > 'M' then 1 else 0 end) HdlM," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and cpb.hdl > 50 and cp.gender > 'F' then 1 else 0 end) HdlF," + 
			"	 sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) and cpb.ldl < 100 then 1 else 0 end) Ldl" + 
			"	 	 " + 
			"	 	from comed_participants_biometrics cpb " + 
			"		left join comed_participants cp on cp.id = cpb.participant_id" + 
			"		left join comed_client_assessment cpa on cpa.client_id = cp.client_id" + 
			"		where " + 
			"		cpa.status = true " + 
			"	 	and cast(cpb.sistolic as DECIMAL(6,2)) >= 0										" + 
			"		and cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)" + 
			"	 ) as results";
	public static final String HEALTH_OVERVIEW_RESULTS_REPORT = "select 			" + 
			"results.sistolic as sistolic,			" + 
			"results.diastolic as diastolic,						" + 
			"results.cholesterol as cholesterol,			" + 
			"results.hdl as hdl," + 
			"results.ldl as ldl," + 
			"results.triglycerides as triglycerides," + 
			"results.glucose as glucose," + 
			"results.hba1c as hba1c," + 
			"results.waist as waist," + 
			"results.body_fat as body_fat,						" +
			"results.height as height,  " + 
			"results.weight as weight  "+
			"from 				" + 
			"(select				 	" + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) then cast(cpb.sistolic as int) else 0 end)/				CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END sistolic,				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.diastolic as int) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  diastolic,				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)  then cast(cpb.cholesterol as int) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  cholesterol,				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)  then cast(cpb.hdl as int) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  hdl,				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) then cast(cpb.ldl as int) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END ldl, 				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.triglycerides as int) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  triglycerides, 				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.glucose as int) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  glucose, 				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.hba1c as decimal) else 0 end)/CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  hba1c, 				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.waist as int) else 0 end) 				 /CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  waist, 				 " + 
			"	 	sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.body_fat as int) else 0 end) 				 /CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  body_fat, 				  				 	" +
			"       sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.height as decimal) else 0 end) 				 /CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  height,  " + 
			"		sum(case when cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)   then cast(cpb.weight as int) else 0 end) 				 /CASE count(*)   WHEN 0 THEN 1   ELSE count(*) END  weight       "+
			"	 from comed_participants_biometrics cpb  					" + 
			"	 left join comed_participants cp on cp.id = cpb.participant_id 					" + 
			"	 left join comed_client_assessment cpa on cpa.client_id = cp.client_id 					" + 
			"	 where  					" + 
			"	 cpa.status = true  				 	" + 
			"	 and cast(cpb.sistolic as DECIMAL) >= 0										 					" + 
			"	 and cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id) 								" + 
			"	 and cp.id = :pid 				" + 
			"	) " + 
			"as results";
	
	
	public static final String BLOOD_HISTORY = "select  create_date,sistolic,diastolic from comed_participants_biometrics where participant_id = :pid order by create_date desc"; 
	public static final String CHOLESTEROL_HISTORY = "select  create_date,cholesterol from comed_participants_biometrics where participant_id = :pid order by create_date desc";
	public static final String HDL_HISTORY = "select  create_date,hdl from comed_participants_biometrics where participant_id = :pid order by create_date desc";
	public static final String LDL_HISTORY = "select  create_date,ldl from comed_participants_biometrics where participant_id = :pid order by create_date desc";
	public static final String TRIGLYCERIDES_HISTORY = "select  create_date,triglycerides from comed_participants_biometrics where participant_id = :pid order by create_date desc";
	public static final String WAIST_HISTORY = "select  create_date,waist from comed_participants_biometrics where participant_id = :pid order by create_date desc";
			
			
		
			
	
	
	

}
