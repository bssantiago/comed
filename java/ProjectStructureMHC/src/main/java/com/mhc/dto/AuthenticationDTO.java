package com.mhc.dto;

import lombok.Data;

public @Data class AuthenticationDTO {

    private String requestedBy;
    private String sourceIP;
    private String documentId;
    private String nonce;
    private String path;
    private String method;

    public AuthenticationDTO() {

    }

    public AuthenticationDTO(String requestedBy, String sourceIP, String documentId, String nonce, String path, String method) {
        this.requestedBy = requestedBy;
        this.sourceIP = sourceIP;
        this.documentId = documentId;
        this.nonce = nonce;
        this.path = path;
        this.method = method;
    }

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

    
    
    
}
