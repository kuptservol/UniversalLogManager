package ru.skuptsov.logviewer.test.model;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.skuptsov.logviewer.annotation.JSON;
import ru.skuptsov.logviewer.annotation.XPATH;
import ru.skuptsov.logviewer.model.LogField;
import ru.skuptsov.logviewer.model.LogMessage;

@Entity
@Table(name = "MSGLOG")
public class HibernateJSONLogObjectModel implements LogMessage {

	private Date timestamp;
	private String messageID;
	private String mediationName;
	Integer id;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public void setMediationName(String mediationName) {
		this.mediationName = mediationName;
	}

	@Column(name = "timestamp")
	public Date getTimestamp() {
		return new Date();
	}

	@Column(name = "messageid")
	@JSON(expression = "$.logObject.messageId")
	public String getMessageID() {
		return messageID;
	}

	@Column(name = "mediationname")
	@JSON(expression = "$.logObject.mediationName")
	public String getMediationName() {
		return mediationName;
	}
}
