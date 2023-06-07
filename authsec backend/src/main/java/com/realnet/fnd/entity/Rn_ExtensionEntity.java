package com.realnet.fnd.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Rn_ExtensionEntity extends Rn_FlexEntity {
	@Column(name = "EXTN1")
	private String extn1;
	@Column(name = "EXTN2")
	private String extn2;
	@Column(name = "EXTN3")
	private String extn3;
	@Column(name = "EXTN4")
	private String extn4;
	@Column(name = "EXTN5")
	private String extn5;
	@Column(name = "EXTN6")
	private String extn6;
	@Column(name = "EXTN7")
	private String extn7;
	@Column(name = "EXTN8")
	private String extn8;
	@Column(name = "EXTN9")
	private String extn9;
	@Column(name = "EXTN10")
	private String extn10;
	@Column(name = "EXTN11")
	private String extn11;
	@Column(name = "EXTN12")
	private String extn12;
	@Column(name = "EXTN13")
	private String extn13;
	@Column(name = "EXTN14")
	private String extn14;
	@Column(name = "EXTN15")
	private String extn15;

	public Rn_ExtensionEntity() {
		super();
	}
	
	public Rn_ExtensionEntity(String extn1, String extn2, String extn3, String extn4, String extn5, String extn6,
			String extn7, String extn8, String extn9, String extn10, String extn11, String extn12, String extn13,
			String extn14, String extn15) {
		super();
		this.extn1 = extn1;
		this.extn2 = extn2;
		this.extn3 = extn3;
		this.extn4 = extn4;
		this.extn5 = extn5;
		this.extn6 = extn6;
		this.extn7 = extn7;
		this.extn8 = extn8;
		this.extn9 = extn9;
		this.extn10 = extn10;
		this.extn11 = extn11;
		this.extn12 = extn12;
		this.extn13 = extn13;
		this.extn14 = extn14;
		this.extn15 = extn15;
	}

	public String getExtn1() {
		return extn1;
	}

	public void setExtn1(String extn1) {
		this.extn1 = extn1;
	}

	public String getExtn2() {
		return extn2;
	}

	public void setExtn2(String extn2) {
		this.extn2 = extn2;
	}

	public String getExtn3() {
		return extn3;
	}

	public void setExtn3(String extn3) {
		this.extn3 = extn3;
	}

	public String getExtn4() {
		return extn4;
	}

	public void setExtn4(String extn4) {
		this.extn4 = extn4;
	}

	public String getExtn5() {
		return extn5;
	}

	public void setExtn5(String extn5) {
		this.extn5 = extn5;
	}

	public String getExtn6() {
		return extn6;
	}

	public void setExtn6(String extn6) {
		this.extn6 = extn6;
	}

	public String getExtn7() {
		return extn7;
	}

	public void setExtn7(String extn7) {
		this.extn7 = extn7;
	}

	public String getExtn8() {
		return extn8;
	}

	public void setExtn8(String extn8) {
		this.extn8 = extn8;
	}

	public String getExtn9() {
		return extn9;
	}

	public void setExtn9(String extn9) {
		this.extn9 = extn9;
	}

	public String getExtn10() {
		return extn10;
	}

	public void setExtn10(String extn10) {
		this.extn10 = extn10;
	}

	public String getExtn11() {
		return extn11;
	}

	public void setExtn11(String extn11) {
		this.extn11 = extn11;
	}

	public String getExtn12() {
		return extn12;
	}

	public void setExtn12(String extn12) {
		this.extn12 = extn12;
	}

	public String getExtn13() {
		return extn13;
	}

	public void setExtn13(String extn13) {
		this.extn13 = extn13;
	}

	public String getExtn14() {
		return extn14;
	}

	public void setExtn14(String extn14) {
		this.extn14 = extn14;
	}

	public String getExtn15() {
		return extn15;
	}

	public void setExtn15(String extn15) {
		this.extn15 = extn15;
	}
}
