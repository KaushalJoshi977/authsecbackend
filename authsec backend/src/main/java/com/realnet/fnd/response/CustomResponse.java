package com.realnet.fnd.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomResponse extends PageResponse {
	@ApiModelProperty(required = true, value = "")
	 private List<? extends Object> items;
	//private List<Object> items;
}
