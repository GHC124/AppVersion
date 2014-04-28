package com.ghc.appversion.service.jpa.admin;

public interface SQLConstants {
	//
	// Database constants
	//
	String PLATFORM_TYPE_ANDROID = "ANDROID";
	String PLATFORM_TYPE_IOS = "IOS";
	
	
	//
	// SQL Query constants
	//
	String ORDER_BY = ":orderBy";
	String SORT = ":sort";
	String LIMIT = "limit";
	String OFFSET = "offset";
	
	/**
	 * Select all users information(email, groups)
	 */
	String USER_SUMMARY_QUERY = "SELECT distinct USER_ID, EMAIL, GROUP_CONCAT(GROUP_NAME) as GROUPNAMES FROM view_user_group_summary " +
			"WHERE 1 group by USER_ID ORDER BY :orderBy :sort LIMIT :limit OFFSET :offset";
	
	String FILTER_GROUP = ":filterGroup";
	/**
	 * Select all users information(email, groups) 
	 * and filter by group
	 */
	String USER_SUMMARY_FILTER_GROUP_QUERY = "SELECT distinct USER_ID, EMAIL, GROUP_CONCAT(GROUP_NAME) as GROUPNAMES " +
			"FROM (select * from view_user_group_summary where GROUP_ID in ( :filterGroup )) as t " +
			"WHERE 1 group by USER_ID ORDER BY :orderBy :sort LIMIT :limit OFFSET :offset";
	
	/**
	 * Select all groups information(name, members count)
	 */
	String GROUP_SUMMARY_QUERY = "SELECT ID, NAME, (SELECT COUNT(ID) FROM user_group WHERE GROUP_ID = g.ID) as MEMBERS FROM groups g WHERE 1 " +
			"ORDER BY :orderBy :sort LIMIT :limit OFFSET :offset";
	
	String USER_ID = "userId";
	/**
	 * Select all groups and show groups that a user joined
	 */
	String GROUP_USER_CHECK_QUERY = "SELECT g.ID, g.NAME, (SELECT ID FROM user_group WHERE USER_ID = :userId AND GROUP_ID = g.ID LIMIT 1) " +
             "as USER_GROUP_ID FROM groups as g WHERE 1 ORDER BY :orderBy :sort LIMIT :limit OFFSET :offset";
		
	String GROUP_ID = "groupId";
	/**
	 * Count members in a group
	 */
	String GROUP_MEMBERS_COUNT_QUERY = "SELECT COUNT(ID) FROM user_group WHERE GROUP_ID = :groupId";
	/**
	 * Select members in a group
	 */
	String GROUP_MEMBERS_QUERY = "SELECT USER_ID,EMAIL,USER_GROUP_ID FROM view_user_group_summary WHERE GROUP_ID=:groupId " +
			"ORDER BY :orderBy :sort LIMIT :limit OFFSET :offset";

	/**
	 * Select all users and show that user joined a specific group or not
	 */
	String USER_GROUP_CHECK_QUERY = "SELECT ID, EMAIL,(SELECT ID FROM user_group WHERE USER_ID = u.ID AND GROUP_ID = :groupId LIMIT 1) as USER_GROUP_ID " +
			"FROM users as u WHERE 1 ORDER BY :orderBy :sort LIMIT :limit OFFSET :offset";
	
	String APP_ID = "appId";
	/**
	 * Select the lastest version of app 
	 */
	String APP_LASTEST_VERSION_QUERY = "SELECT ID,VERSION,MAX(RELEASE_DATE),RELEASE_NOTE,APP_ID,APP_DOWNLOAD_URL,APP_SIZE FROM app_versions " +
			"WHERE APP_ID = :appId GROUP BY APP_ID";
	
	/**
	 * Select all versions of app
	 */
	String APP_SELECT_ALL_VERSIONS_QUERY = "SELECT ID,VERSION,RELEASE_DATE,RELEASE_NOTE,APP_ID,APP_DOWNLOAD_URL,APP_SIZE FROM app_versions WHERE APP_ID = :appId";
	
	String APP_NAME = "appName";
	/**
	 * Select app by name
	 */
	String APP_SELECT_BY_NAME_QUERY = "SELECT * FROM apps WHERE NAME LIKE :appName";
}
