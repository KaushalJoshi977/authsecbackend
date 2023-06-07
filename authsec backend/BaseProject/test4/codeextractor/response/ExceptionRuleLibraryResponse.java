package com.realnet.codeextractor.response;

import java.util.List;

import com.realnet.codeextractor.entity.Rn_Bcf_Exception_Rules;
import com.realnet.fnd.response.PageResponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExceptionRuleLibraryResponse extends PageResponse {
  @ApiModelProperty(required = true, value = "")
  private List<Rn_Bcf_Exception_Rules> items;
}
