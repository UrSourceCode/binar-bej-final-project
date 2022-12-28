package com.binar.flyket.utils;

import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constants {

    public static final String SUCCESS_MSG = "success";
    public static final String CREATED_MSG = "created";
    public static final String ERROR_MSG = "error";
    public static final String UPDATED_MSG = "updated";
    public static final String DELETED_MSG = "deleted";

    public static final String UPLOAD_FAILED = "upload failed";

    public static final String EMPTY_MSG = "empty";

    public static final String ROLE_NOT_FOUND = "role not found";
    public static final String AIRPORT_NOT_FOUND = "airport not found";
    public static final String AIRCRAFT_NOT_FOUND = "aircraft not found";
    public static final String TICKET_NOT_FOUND = "ticket not found";
    public static final String COUNTRY_NOT_FOUND = "country not found";

    public static final String FROM_AIRPORT_NOT_FOUND_MSG = "origin airport not found";
    public static final String TO_AIRPORT_NOT_FOUND_MSG = "destination airport not found";
    public static final String ROUTE_NOT_FOUND_MSG = "route not found";
    public static final String NOT_FOUND_MSG = "not found";
    public static final String ALREADY_EXIST_MSG = "already exist";
    public static final String INVALID_EMAIL_MSG = "Invalid email";
    public static final String INVALID_PASSWORD_MSG = "8 to 24 characters. Must include uppercase and lowercase letters, a number " +
            "and a special character";

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]" +
            "(.[_A-Za-z0-9-]+)@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)" +
            "(.[A-Za-z]{2,})$";

    public static String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$";

    /*
     * @Param email
     *
     * @return true
     */
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    /*
     * Random Identifier result :
     * [0] -> time_low
     * [1] -> time_mid
     * [2] -> time_hi_and_version
     * [3] -> clock_seq_hi_and_res
     * [4] -> node
     */
    public static String[] randomIdentifier(String s) {
        byte[] b = s.getBytes();
        UUID uuid = UUID.nameUUIDFromBytes(b);
        return uuid.toString().split("-");
    }

    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    public static Sort.Direction sortDirection(String direction) {
        if (direction.equals("latest")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("newest")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }

    Constants() {}
}
