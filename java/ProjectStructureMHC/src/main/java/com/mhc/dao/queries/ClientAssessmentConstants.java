package com.mhc.dao.queries;

public class ClientAssessmentConstants {
	public static final String SELECT_CLIENT_ASSESSMENTS_MARKED = "select count(*) as count FROM comed_client_assessment where client_id = :client_id and program_id=:program_id and marked = true";
	public static final String UPDATE_CLIENT_ASSESMENTS = "UPDATE comed_client_assessment SET marked = true where client_id = ? and program_id=?;";
	public static final String SELECT_CLIENT_ASSESMENTS = "SELECT cca.*,cc.name as client_name,count(*) OVER() AS full_count FROM comed_client_assessment as cca inner join comed_clients cc on cca.client_id = cc.id WHERE cca.status = true order by cca.creation_date desc OFFSET ? LIMIT ?;";
	
}
