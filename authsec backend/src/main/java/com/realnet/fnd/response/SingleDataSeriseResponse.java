//This is a common http response model for providing data series

package com.realnet.fnd.response;

import java.util.List;

import com.realnet.fnd.entity.SingleSerise;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SingleDataSeriseResponse extends OperationResponse {
    private List<SingleSerise> items;
}
