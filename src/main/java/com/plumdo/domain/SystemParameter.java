package com.plumdo.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;


/**
 * The persistent class for the stock_info database table.
 * 
 */
@Entity
@Table(name="system_parameter", catalog="plumdo_stock")
@DynamicInsert
@DynamicUpdate
public class SystemParameter implements Serializable {
	private static final long serialVersionUID = 1L;
	private int parameterId;
	private String parameterName;
	private String parameterValue;

	public SystemParameter() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="parameter_id")
	public int getParameterId() {
		return parameterId;
	}

	public void setParameterId(int parameterId) {
		this.parameterId = parameterId;
	}

	@Column(name="parameter_name")
	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	@Column(name="parameter_value")
	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

}