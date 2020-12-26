package com.ncedu.cheetahtest.exception.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Failed uploading photo process ")
public class FailedFileUploadingException extends RuntimeException{
}
