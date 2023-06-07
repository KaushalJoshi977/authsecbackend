package com.realnet.users.response;

import com.realnet.fnd.response.OperationResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends OperationResponse {
	@ApiModelProperty(required = true, value = "")
	private UserItem item;

}
