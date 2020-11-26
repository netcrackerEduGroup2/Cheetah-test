package com.ncedu.cheetahtest.exception.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Pagination Request")
public class PaginationWrongInputException extends RuntimeException{

}
