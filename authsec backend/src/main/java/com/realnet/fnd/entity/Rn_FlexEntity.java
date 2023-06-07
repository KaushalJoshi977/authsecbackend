package com.realnet.fnd.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Rn_FlexEntity {
	@Column(name = "FLEX1")
	private String flex1;
	@Column(name = "FLEX2")
	private String flex2;
	@Column(name = "FLEX3")
	private String flex3;
	@Column(name = "FLEX4")
	private String flex4;
	@Column(name = "FLEX5")
	private String flex5;

	public Rn_FlexEntity() {
		super();
	}

	public Rn_FlexEntity(String flex1, String flex2, String flex3, String flex4, String flex5) {
		super();
		this.flex1 = flex1;
		this.flex2 = flex2;
		this.flex3 = flex3;
		this.flex4 = flex4;
		this.flex5 = flex5;
	}

	public String getFlex1() {
		return flex1;
	}

	public void setFlex1(String flex1) {
		this.flex1 = flex1;
	}

	public String getFlex2() {
		return flex2;
	}

	public void setFlex2(String flex2) {
		this.flex2 = flex2;
	}

	public String getFlex3() {
		return flex3;
	}

	public void setFlex3(String flex3) {
		this.flex3 = flex3;
	}

	public String getFlex4() {
		return flex4;
	}

	public void setFlex4(String flex4) {
		this.flex4 = flex4;
	}

	public String getFlex5() {
		return flex5;
	}

	public void setFlex5(String flex5) {
		this.flex5 = flex5;
	}

}
