package com.realnet.fnd.response;

import java.util.List;

import com.realnet.fnd.entity.Rn_Dynamic_Transaction;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Rn_DynamicTransactionResponse extends PageResponse {
  @ApiModelProperty(required = true, value = "")
  private List<Rn_Dynamic_Transaction> items;
}
