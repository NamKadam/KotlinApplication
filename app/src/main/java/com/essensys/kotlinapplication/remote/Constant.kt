package com.essensys.kotlinapplication.remote

class Constant {
    interface ServerEndpoint {
        companion object {
            //String LOGIN = "ws-login/ws-login.php/"; // Added by SM
            const val LOGIN = "ws-user/ws-login.php/" // Added by SM
            const val FORGOT_PASSWORD = "ws-user/ws-login.php/" // Added by SM
            const val GET_HOME_DATA = "ws-work-management/ws-home.php/"

            //Todays WorkList
            const val TODAYS_WORKLIST =
                "ws-work-management/ws-get-todayswork-list.php/" //added by ND
            const val GET_FIELDS = "ws-work-management/ws-get-fields.php/"
            const val GET_WORK_DET = "ws-work-management/ws-get-work-details.php/" //added by ND
            const val UPDATE_WORK_DET = "ws-work-management/ws-work-details_action.php/"
            const val UPDATE_ASSIGNEE = "ws-work-management/ws-work-assign-user-action.php/"

            //CreateCall
            const val CALL_LIST = "ws-new-calls-management/ws-get-customer-list.php/"
            const val CREATE_CALL = "ws-new-calls-management/ws-get-new-call-details.php/"
            const val SAVE_CALL = "ws-new-calls-management/ws-create-new-call-action.php/"
            const val ADD_NEW_CALL =
                "ws-new-calls-management/ws-add-customer-create-new-call-action.php/"

            //Customer Management
            const val GET_CUST_LIST = "ws-customer/ws-get-customer-list.php/"
            const val GET_CUST_DET = "ws-customer/ws-customer-details.php/"
            const val ADD_NEW_CUST = "ws-customer/ws-new-customer-action.php/"
            const val UPDATE_NEW_CUST = "ws-customer/ws-update-customer-details.php/"
        }
    }

    interface SharedPreferences {
        companion object {
            const val LOGGED_IN_USER = "LOGGED_IN_USER"
            const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
            const val IS_USER_LOGGED_OUT = "IS_USER_LOGGED_OUT"
        }
    }

    interface Common {
        companion object {
            const val SHARED_PREFERENCES = "SOLONOX_SHARED_PREFERENCES"
        }
    }

    interface IntentExtras {
        companion object {
            const val NAV_VIEW_ID = "NAV_VIEW_ID"
            const val DATA = "DATA"
        }
    }

    interface InterfaceCommon {
        companion object {
            const val EDIT = "EDIT"
            const val ADD = "ADD"
        }
    }

    interface DownloadInvoice {
        companion object {
            const val COMPANY_ID = "company_id"
            const val INV_ID = "invId"
        }
    }

    interface DateFormat {
        companion object {
            const val DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy"
            const val DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd" //In which you need put here
            const val DATE_FORMAT_DD_MM_YY = "dd/MM/yy"
            const val DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy"
            const val DATE_FORMAT_DD_MM_YY_KK_MM_SS = "dd/MM/yy kk:mm:ss"
            const val DATE_FORMAT_DD_MMM_YYYY_KK_MM_SS = "dd-MMM-yyyy kk:mm:ss"
        }
    }
}