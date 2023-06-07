package com.realnet.fnd.response;

import java.util.List;

import com.realnet.fnd.entity.Rn_Function_Register;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Rn_Function_Register_Response extends PageResponse {
	@ApiModelProperty(required = true, value = "")
	private List<Rn_Function_Register> items;
}
