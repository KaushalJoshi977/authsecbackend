package com.realnet.fnd.response;

import java.util.List;

import com.realnet.fnd.entity.Rn_Forms_Setup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Rn_Forms_SetupResponse extends PageResponse {
  @ApiModelProperty(required = true, value = "")
  private List<Rn_Forms_Setup> items;
}
