package com.example.employeeevaluation.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppConstants {

    //	Pagination config
    public static final String PAGING_DEFAULT_PAGE_NUMBER = "0";
    public static final String PAGING_DEFAULT_PAGE_SIZE = "3";
    public static final int PAGING_MAX_PAGE_SIZE = 10;

    // Column config
    public static final String CREATED_AT = "createdAt";

    //	Jwt config
    public static final int JWT_EXPIRE = 24;

    //  Logger config
    public static final Logger INFOR_LOGGER = LogManager.getLogger("InfoLogger");
    public static final Logger ERROR_LOGGER = LogManager.getLogger("ErrorLogger");
    public static final Logger WARNING_LOGGER = LogManager.getLogger("WarningLogger");
}

