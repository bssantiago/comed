package com.mhc.dao.queries;

public class ClientConstants {

    public static final String SELECT_CLIENTS_WITH_PROGRAMS = 
    		"	SELECT distinct cc.id,cc.name,cca.program_id, cca.program_display_name,cca.status,cca.reward_date FROM comed_clients cc " + 
    		"    left join comed_client_assessment cca " + 
    		"    	on cc.id = cca.client_id " + 
    		"		where status = true or status IS NULL order by cc.name asc;";
    
    public static final String SELECT_CLIENT_BY_ID = "SELECT *,count(*) OVER() as quantity FROM comed_clients WHERE id = :id";
    public static final String SELECT_CLIENT_ID_BY_PLATFORM_ID = "SELECT cc.* FROM comed_clients cc JOIN comed_clients_platform ccp ON (cc.id = ccp.client_id ) where ccp.platform_id = :platform_id";
    
}
