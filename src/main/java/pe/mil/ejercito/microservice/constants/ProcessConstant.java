package pe.mil.ejercito.microservice.constants;

import lombok.experimental.UtilityClass;

/**
 * ProcessConstant
 * <p>
 * ProcessConstant class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author bxcode
 * @author bacsystem.sac@gmail.com
 * @since 10/03/2024
 */

@UtilityClass
public class ProcessConstant {
    public static final String MICROSERVICE_PATH_CONTEXT = "";

    public static final String FIND_ALL_MODULE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/module-status";
    public static final String FIND_BY_ID_MODULE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/module-status/id/{statusId}/status";
    public static final String FIND_BY_UUID_MODULE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/module-status/uuid/{uuId}/status";
    public static final String CREATE_MODULE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/module-status/create";
    public static final String UPDATE_MODULE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/module-status/update";
    public static final String DELETE_MODULE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/module-status/delete/uuId/{uuId}/status";

    public static final String FIND_ALL_PROFILE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/profile-status";
    public static final String FIND_BY_ID_PROFILE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/profile-status/id/{statusId}/status";
    public static final String FIND_BY_UUID_PROFILE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/profile-status/uuid/{uuId}/status";
    public static final String CREATE_PROFILE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/profile-status/create";
    public static final String UPDATE_PROFILE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/profile-status/update";
    public static final String DELETE_PROFILE_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/profile-status/delete/uuId/{uuId}/status";

    public static final String FIND_ALL_USER_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/user-status";
    public static final String FIND_BY_ID_USER_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/user-status/id/{statusId}/status";
    public static final String FIND_BY_UUID_USER_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/user-status/uuid/{uuId}/status";
    public static final String CREATE_USER_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/user-status/create";
    public static final String UPDATE_USER_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/user-status/update";
    public static final String DELETE_USER_STATUS_PATH = MICROSERVICE_PATH_CONTEXT + "/user-status/delete/uuId/{uuId}/status";

    public static final String FIND_ALL_PROFILE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles";
    public static final String FIND_BY_ID_PROFILE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles/id/{profileId}/profile";
    public static final String FIND_BY_UUID_PROFILE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles/uuid/{uuId}/profile";
    public static final String CREATE_PROFILE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles/create";
    public static final String UPDATE_PROFILE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles/update";
    public static final String DELETE_PROFILE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles/delete/uuId/{uuId}/profile";

    public static final String FIND_ALL_PERSON_PATH = MICROSERVICE_PATH_CONTEXT + "/persons";
    public static final String FIND_BY_ID_PERSON_PATH = MICROSERVICE_PATH_CONTEXT + "/persons/id/{personId}/person";
    public static final String FIND_BY_UUID_PERSON_PATH = MICROSERVICE_PATH_CONTEXT + "/persons/uuid/{uuId}/person";
    public static final String CREATE_PERSON_PATH = MICROSERVICE_PATH_CONTEXT + "/persons/create";
    public static final String UPDATE_PERSON_PATH = MICROSERVICE_PATH_CONTEXT + "/persons/update";
    public static final String DELETE_PERSON_PATH = MICROSERVICE_PATH_CONTEXT + "/persons/delete/uuId/{uuId}/person";


    public static final String FIND_ALL_USER_PATH = MICROSERVICE_PATH_CONTEXT + "/users";
    public static final String FIND_BY_ID_USER_PATH = MICROSERVICE_PATH_CONTEXT + "/users/id/{userId}/user";
    public static final String FIND_BY_UUID_USER_PATH = MICROSERVICE_PATH_CONTEXT + "/users/uuid/{uuId}/user";
    public static final String CREATE_USER_PATH = MICROSERVICE_PATH_CONTEXT + "/users/create";
    public static final String UPDATE_USER_PATH = MICROSERVICE_PATH_CONTEXT + "/users/update";
    public static final String DELETE_USER_PATH = MICROSERVICE_PATH_CONTEXT + "/users/delete/uuId/{uuId}/user";


    public static final String FIND_ALL_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/modules";
    public static final String FIND_BY_ID_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/modules/id/{moduleId}/module";
    public static final String FIND_BY_UUID_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/modules/uuid/{uuId}/module";
    public static final String CREATE_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/modules/create";
    public static final String UPDATE_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/modules/update";
    public static final String DELETE_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/modules/delete/uuId/{uuId}/module";

    public static final String FIND_ALL_PROFILE_OPTION_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles-option-modules";
    public static final String FIND_BY_ID_PROFILE_OPTION_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles-option-modules/id/{moduleId}/options";
    public static final String FIND_BY_UUID_PROFILE_OPTION_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles-option-modules/uuid/{uuId}/options";
    public static final String CREATE_PROFILE_OPTION_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles-option-modules/create";
    public static final String UPDATE_PROFILE_OPTION_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles-option-modules/update";
    public static final String DELETE_PROFILE_OPTION_MODULE_PATH = MICROSERVICE_PATH_CONTEXT + "/profiles-option-modules/delete/uuId/{uuId}/options";
    public static final String MICROSERVICE_PROCESS_VALIDATION_PARAMETER_REQUIRED = "error the fields is required";
}


