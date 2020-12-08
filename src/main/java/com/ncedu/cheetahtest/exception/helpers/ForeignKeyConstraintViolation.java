package com.ncedu.cheetahtest.exception.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Trying to delete used as foreign key entity")
public class ForeignKeyConstraintViolation extends RuntimeException {
}
