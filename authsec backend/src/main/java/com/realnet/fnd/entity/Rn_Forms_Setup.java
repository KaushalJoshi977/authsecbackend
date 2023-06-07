package com.realnet.fnd.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//@Data
//@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "RN_FORMS_SETUP_T")
public class Rn_Forms_Setup extends Rn_Who_Columns {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FORM_ID")
	private int form_id;

	@Column(name = "FORM_NAME")
	private String form_name;

	@Column(name = "FORM_DESC")
	private String form_desc;

	@Column(name = "RELATED_TO")
	private String related_to;

	@Column(name = "PAGE_EVENT")
	private String page_event;

	@Column(name = "BUTTON_CAPTION")
	private String button_caption;

	@OneToMany(mappedBy = "rn_forms_setup", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Rn_Forms_Component_Setup> components;

	public int getForm_id() {
		return form_id;
	}

	public void setForm_id(int form_id) {
		this.form_id = form_id;
	}

	public String getForm_name() {
		return form_name;
	}

	public void setForm_name(String form_name) {
		this.form_name = form_name;
	}

	public String getForm_desc() {
		return form_desc;
	}

	public void setForm_desc(String form_desc) {
		this.form_desc = form_desc;
	}

	public String getRelated_to() {
		return related_to;
	}

	public void setRelated_to(String related_to) {
		this.related_to = related_to;
	}

	public String getPage_event() {
		return page_event;
	}

	public void setPage_event(String page_event) {
		this.page_event = page_event;
	}

	public String getButton_caption() {
		return button_caption;
	}

	public void setButton_caption(String button_caption) {
		this.button_caption = button_caption;
	}

	public List<Rn_Forms_Component_Setup> getComponents() {
		return components;
	}

	public void setComponents(List<Rn_Forms_Component_Setup> components) {
		this.components = components;
	}
	
}