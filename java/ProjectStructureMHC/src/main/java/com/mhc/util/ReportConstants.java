package com.mhc.util;

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
			"	 	and cast(cpb.sistolic as int) >= 0										" + 
			// "		-- and cpb.create_date in (select MAX(create_date) from comed_participants_biometrics where participant_id = cpb.participant_id)" + 
			"	 ) as results";
	public static final String HEALTH_OVERVIEW_RESULTS_REPORT = "";
	public static final String NEGATIVE_RESULTS_REPORT = "";
	public static final String POSITIVE_RESULTS_REPORT = "";

}
