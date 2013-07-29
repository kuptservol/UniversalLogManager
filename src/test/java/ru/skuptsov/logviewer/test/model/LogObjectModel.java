package ru.skuptsov.logviewer.test.model;

import java.util.Date;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ru.skuptsov.logviewer.annotation.XPATH;
import ru.skuptsov.logviewer.model.LogField;
import ru.skuptsov.logviewer.model.LogMessage;

@Entity
@Table(name = "MSGLOG")
public class LogObjectModel implements LogMessage {

	private Date timestamp;
	private String messageID;
	private String mediationName;
	Integer id;

	@Id
	@Column(name = "ID")
	public Integer getId() {
		return 1;
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
	@XPATH(expression = "/*//*//requestID")
	public String getMessageID() {
		return messageID;
	}

	@Column(name = "mediationname")
	@XPATH(expression = "concat(/*//TARGET_MODULE,' ',/*//TARGET_METHOD,' '," +
			"concat( substring('Request',1, number(*//eventNature = 'ENTRY') *  " +
			"string-length('Request')),  substring('Response',1, number(*//eventNature = 'EXIT')*  " +
			"string-length('Response')) ))")
	public String getMediationName() {
		return mediationName;
	}
}
