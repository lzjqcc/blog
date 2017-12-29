package com.lzj.utils;

import com.google.common.collect.Lists;
import com.lzj.VO.ResponseVO;
import com.lzj.dao.dto.FriendDto;
import com.lzj.exception.SystemException;
import com.lzj.service.impl.FriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

public class ValidatorUtils {
    private final static Logger log = LoggerFactory.getLogger(ValidatorUtils.class);

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public static ValidatorResponse validatorObj(Object object) {

        ValidatorResponse response = new ValidatorResponse();
        if (object == null) {
            response.setSuccess(false);
            response.setErrorList(Lists.newArrayList("验证对象为空"));
        }

        Set<ConstraintViolation<Object>> violationSet = validator.validate(object);
        if (violationSet.size() == 0) {
            response.setSuccess(true);
            return response;
        }
        List<String> errorList = new ArrayList<>();
        for (ConstraintViolation<Object> constraintViolation : violationSet) {
            errorList.add(constraintViolation.toString());
        }
        response.setErrorList(errorList);
        return response;
    }
    public static ResponseVO validatorData(Object o) {
        ResponseVO responseVO = new ResponseVO();
        ValidatorUtils.ValidatorResponse response = ValidatorUtils.validatorObj(o);
        if (!response.getSuccess()) {
            log.error("数据校验失败" + response.getErrorList());
            responseVO.setMessage("数据校验失败" + response.getErrorList());
            responseVO.setSuccess(false);
            return responseVO;
        }
        responseVO.setSuccess(true);
        return responseVO;
    }
    public static class ValidatorResponse{
        private boolean success;
        private List<String> errorList;

        public List<String> getErrorList() {
            return errorList;
        }

        public void setErrorList(List<String> errorList) {
            this.errorList = errorList;
        }

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

    }
}
